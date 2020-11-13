import React , { Component } from 'react';
import { connect } from 'react-redux'
import { Redirect } from 'react-router-dom'
import {Button} from 'antd'
import {setUpdateToNull} from '../actions/eventActions'

import 'antd/dist/antd.css';

class eventDetail extends Component {

  handleEditButton =(eventId)=>{
    this.props.setUpdateToNull();
    this.props.history.push('/editevent/' + eventId)
  }

  handleBackButton =()=>{
    this.props.history.push('/myevents');
  }

  state = {
    dataSource:[],
    visible: false,
    friends:[],
    selectUser:[],
    eventChoose:""
};

  render(){
    const { event, isLogin} = this.props;
    if (isLogin !== 'yes') return <Redirect to='/login' /> 
    if (event) {
      return (
        <div className="container section event-details">
          <h5>Event Detail</h5>
          <div className="card blue lighten-1">
            <div className="card-content white-text">
              <h6>Event Title:</h6>
              <p>{event.eventTitle}</p>
              <h6 >Event Note:</h6> 
              <p >{event.note}</p>
              <h6 >Event Start Time:</h6>
              <p>{event.startTime}</p>
              <h6>Event End Time:</h6>
              <p>{event.endTime}</p>
            </div>
          </div>
          <Button  type="primary" onClick={this.handleEditButton.bind(this,event.eventId)} style={{float: 'left'}}>Edit</Button>
          <Button  type="primary" onClick={this.handleBackButton.bind(this)} style={{float: 'right'}}>Go Back</Button>
        </div>
      )
    } else {
      return (
        <div className="container center">
          <p>Loading event...</p>
        </div>
      )
    }
  }
  }
  

const handleEvents =(events,id)=>{
    for(var i = 0; i< events.length; i++){
        if (parseInt(events[i]['eventId']) === parseInt(id)){
            return events[i];
        }
  }
}
const mapStateToProps = (state, ownProps) => {
  const id = ownProps.match.params.eventId;
  const events =  state.event.events;
  const event = handleEvents(events,id);
  return {
    event: event,
    isLogin: state.auth.isLogin,
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    setUpdateToNull: () => dispatch(setUpdateToNull()),
  }
}

export default connect(mapStateToProps,mapDispatchToProps)(eventDetail)