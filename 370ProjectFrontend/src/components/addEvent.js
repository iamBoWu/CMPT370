import React, { Component } from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { connect } from 'react-redux'
import { addNewEvent,handleConflict} from '../actions/eventActions'
import { Redirect } from 'react-router-dom'
import { message} from 'antd';

message.config({
  top: 150,
  duration: 2,
  maxCount: 3,
});

class AddEvent extends Component {
  state = {
    userId:null,
    eventTitle: '',
    note: '',
    priority: '0',
    startDate: new Date(),
    endDate: new Date(),
    startTime: new Date(new Date().toISOString().slice(0,19) + '+06:00').toISOString().slice(0,19).replace('T', ' '),    
    endTime: new Date(new Date().toISOString().slice(0,19) + '+06:00').toISOString().slice(0,19).replace('T', ' ')
  }
 
  handleChange =(e)=>{
    this.setState({
        [e.target.id]:e.target.value,
        userId: this.props.user.userId,
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
    console.log(this.state);
    e.preventDefault();
    console.log(this.props.user.userId);
    this.props.addNewEvent(this.state);
    if (this.props.addSuccess === "yes"){
      this.setState({
        eventTitle:'',
        note:'',
        priority: '0',
        startDate: new Date(),
        endDate: new Date(),
        startTime: new Date(new Date().toISOString().slice(0,19) + '+06:00').toISOString().slice(0,19).replace('T', ' '),    
        endTime: new Date(new Date().toISOString().slice(0,19) + '+06:00').toISOString().slice(0,19).replace('T', ' ')
    })
    }
  }

  handleCancel =(e)=>{
    e.preventDefault();
    this.props.history.push('/')
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
  

  render() {
      const { eventError,addSuccess } = this.props;
      if (addSuccess === 'yes') return <Redirect to='/' /> 
    return (
      <div className="AddEvent container">
        <form onSubmit={this.handleSubmit}>
          <h5 style={{textAlign: 'center', marginTop: '20px'}}>Add Event</h5>
          <div className="input-field">
            <label htmlFor="eventTitle">Title</label>
            <input type="text" id='eventTitle' onChange={this.handleChange}  value={this.state.eventTitle}/>
          </div>
          <div className="input-field">
            <label htmlFor="startdate">Start Date</label>
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
            <label htmlFor="enddate">End Date</label>
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
          <label htmlFor="priority">Priority :
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
          <div className="input-field">
            <label htmlFor="note">Please Enter Notes</label>
            <textarea id="note" className="materialize-textarea"
            onChange={this.handleChange} 
            value={this.state.note}
            style={this.textareastyle}/>
          </div>
          
          
          <div className="input-field">
            <button className="btn blue z-depth-0" style={{float: 'left'}}>Add</button>
            <div className="center red-text">
                        { eventError ? <p>{eventError}</p> : null }
            </div>
          </div>
        </form>
        <button className="btn blue z-depth-0" onClick={this.handleCancel.bind(this)} style={{float: 'right'}}>Cancel</button>
      </div>
    );
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
}

const mapStateToProps = (state) => {
    return {
      eventError: state.event.eventError,
      addSuccess:state.event.addSuccess,
      user: state.auth.user,
      conflictEvents: state.event.conflictEvents,
      conflict: state.event.conflict
    }
  }

const mapDispatchToProps = (dispatch) => {
    return {
        addNewEvent: (event) => dispatch(addNewEvent(event)),
        handleConflict: () => dispatch(handleConflict()),
    }
  }
export default connect(mapStateToProps,mapDispatchToProps)(AddEvent);

