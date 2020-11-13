import React, { Component } from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { connect } from 'react-redux'
import { updateEvent,handleConflict } from '../actions/eventActions'
import { Redirect } from 'react-router-dom'
import { message} from 'antd';

message.config({
  top: 150,
  duration: 2,
  maxCount: 3,
});

class editEvent extends Component {
  state = {
    eventId:this.props.oldEvent.eventId,
    eventTitle: this.props.oldEvent.eventTitle,
    userId: this.props.user.userId,
    note: this.props.oldEvent.note,
    priority: this.props.oldEvent.priority,
    startDate: new Date(),
    endDate: new Date(),
    startTime: this.props.oldEvent.startTime,
    endTime:this.props.oldEvent.endTime,
  }
 
  handleChange =(e)=>{
    this.setState({
        [e.target.id]:e.target.value
    })
  }
  
  handleChangeStartDate = date => {
    this.setState({
      startDate: date,
      startTime: new Date(date.toISOString().slice(0,19) + '+06:00').toISOString().slice(0,19).replace('T', ' ')
    });
  }

  handleChangeEndDate = date => {
    this.setState({
      endDate: date,
      endTime: new Date(date.toISOString().slice(0,19) + '+06:00').toISOString().slice(0,19).replace('T', ' ')
    });
  }

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.updateEvent(this.state);
    if (this.props.updateSuccess === "yes"){
      this.setState({
        eventTitle:'',
        note:'',
        priority: '0',
        startDate: new Date(),
        endDate: new Date(),
        startTime: new Date().toISOString().slice(0,19).replace('T', ' '),
        endTime:new Date().toISOString().slice(0,19).replace('T', ' '),
    })
    }
  }

  handleCancel =()=>{
    this.props.history.push('/myevents')
  }

  timeConflict = (conflictEvents) =>{
    let events = ""
    for (let i = 0; i < conflictEvents.length; i++) {
      events = events + "eventTitle: " + conflictEvents[i].eventTitle + " ";
    }
    message.warning('event has the time conflict with ' + events);
  }

  componentDidUpdate(){
    let conflict = this.props.conflict;
    if (conflict === 'yes') {
      this.timeConflict(this.props.conflictEvents);
      this.props.handleConflict();
    }
  }

  textareastyle = {
    border: '1px solid #949494',
    borderRadius: '4px',
    overflow: 'auto',
    height: '150px',
    maxHeight: '150px',
    paddingLeft: '15px',
    paddingRight: '15px',
    marginTop: '5px',
    boxSizing: 'border-box'
  }

  render() {
    const { eventError,isLogin,updateSuccess} = this.props;
    if ( isLogin === null) return <Redirect to='/login' /> 
    else if (updateSuccess === 'yes') return <Redirect to='/myevents' />
    return (
      <div className="editEvent container">
        <form onSubmit={this.handleSubmit}>
          <div className="oldevent card-panel">
          <h5 style={{textAlign: 'center', marginTop: '20px'}}>Edit Event</h5>
          </div>
          <label htmlFor="eventTitle">New Title</label>
          <div className="input-field">
            <input type="text" id='eventTitle' onChange={this.handleChange}  value={this.state.eventTitle}/>
          </div>
          <div className="input-field">
            <label htmlFor="startdate">New Start Date</label>
            <br/>
            <DatePicker
              selected={this.state.startDate}
              onChange={this.handleChangeStartDate}
              showTimeSelect
              timeFormat="HH:mm:ss"
              dateFormat="yyyy-MM-dd HH:mm:ss"
            />
          </div>
          <div className="input-field">
            <label htmlFor="enddate">New End Date</label>
            <br/>
            <DatePicker
              selected={this.state.endDate}
              onChange={this.handleChangeEndDate}
              showTimeSelect
              timeFormat="HH:mm:ss"
              dateFormat="yyyy-MM-dd HH:mm:ss"
            />
          </div>
          <div className="input-field">
          <label htmlFor="priority">New Priority :
          <select id="priority"
          style={{display: "block", borderColor: '#949494'}}
          onChange={this.handleChange} value={this.state.priority}
          >
					  <option value="0">0</option>
					  <option value="1">1</option>
					  <option value="2">2</option>
					  <option value="3">3</option>
				  </select></label>
          <br/><br/><br/><br/>
          </div>
          <label htmlFor="note">Please Enter Notes:</label>
          <div className="input-field">
            <textarea id="note" className="materialize-textarea"
            onChange={this.handleChange} 
            value={this.state.note}
            style={this.textareastyle}/>
          </div>
          <div className="input-field">
            <button className="btn blue z-depth-0" style={{float: 'left'}}>Update</button>
            <div className="center red-text">
                        { eventError ? <p>{eventError}</p> : null }
            </div>
          </div>
        </form>
        <button className="btn blue z-depth-0" style={{float: 'right'}} onClick={this.handleCancel.bind(this)}>Cancel</button>
      </div>
    );
  }
}


const handleEvents =(events,id)=>{
  for(var i = 0; i< events.length; i++){
      if (parseInt(events[i]['eventId']) === parseInt(id)){
          return events[i];
      }
}
}

const mapStateToProps = (state,ownProps) => {
    const id = ownProps.match.params.eventId;
    const events =  state.event.events;
    const event = handleEvents(events,id);
    return {
      eventError: state.event.eventError,
      updateSuccess:state.event.updateSuccess,
      user: state.auth.user,
      isLogin: state.auth.isLogin,
      oldEvent: event,
      conflictEvents: state.event.conflictEvents,
      conflict: state.event.conflict
    }
  }

const mapDispatchToProps = (dispatch) => {
    return {
        updateEvent: (event) => dispatch(updateEvent(event)),
        handleConflict: () => dispatch(handleConflict()),
    }
  }
export default connect(mapStateToProps,mapDispatchToProps)(editEvent);
