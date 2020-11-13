import React, { Component } from 'react';
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction' // needed for dayClick
import { confirmAlert } from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css' // Import css
import '../main.scss'
import { connect } from 'react-redux'
import {getEvent} from '../actions/eventActions'
import axios from 'axios'
import { Redirect } from 'react-router-dom'


const apiUrl = 'http://localhost:8080'

class Calendar extends Component {

  calendarComponentRef = React.createRef()
  state = {
    calendarWeekends: true,
    calendarEvents: [],
    calendarAspectRatio: 1.5,
    events:[]
  }

  getAllEvent = (user_id) =>{
    this.setState({
      ...this.state,
      calendarEvents:[]
    })
    axios.get(`${apiUrl}/showAllevent/${user_id}`)
     .then(response=>{
       this.addEventList(response.data)
     })
     .catch(erro =>{
     })
  } 


  addEventList = (eventList) => {
    eventList.map(newEvent => this.addNewEvent(newEvent))
  }

  addNewEvent =(newEvent)=>{
    newEvent={
          id: newEvent.eventId,
          title: newEvent.eventTitle,
          start: newEvent.startTime,
          end: newEvent.endTime
        }
    let calendarEvents = [...this.state.calendarEvents,newEvent]
    this.setState({
      calendarEvents:calendarEvents
      })
  }


  componentDidMount() {
    if(this.props.isLogin !== null){
      this.getAllEvent(this.props.user.userId)
    }
  }

  render() {
    const { isLogin } = this.props; 
    if (isLogin === null ) return <Redirect to='/home' /> 
    return (
      <div className='calendar'>
        <div className='app-calendar'>
          <FullCalendar
            defaultView="dayGridMonth"
            header={{
              left: 'prev,next today',
              center: 'title',
              right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
            }}
            plugins={[ dayGridPlugin, timeGridPlugin, interactionPlugin ]}
            ref={ this.calendarComponentRef }
            weekends={ this.state.calendarWeekends }
            events={ this.state.calendarEvents }
            dateClick={ this.handleDateClick }
            eventClick={ this.handleEventClick }
            aspectRatio={ this.state.calendarAspectRatio }
            />
        </div>
      </div>
    )
  }

  
  handleEventClick= ({event}) => {
    confirmAlert({
      title: event.title,
      message: 'Start Date: ' + event.start +' End Date: ' + event.end,
      buttons: [
        {
          label: 'Close',
        },
        {
          label: 'Delete',
          onClick: () => this.deleteEvent({event})
        }
      ]
    })
  }

  
  deleteEvent = ({event}) => {
    event.remove()

    axios.delete(`${apiUrl}/deleteEvent/${event.id}`)
            .then(()=>{
              this.getAllEvent(this.props.user.userId);
               //We need to update state in store also
              this.props.getEvent(this.props.user.userId);
            }).catch(err=>{

            })
  }

  toggleWeekends = () => {
    this.setState({ // update a property
      calendarWeekends: !this.state.calendarWeekends
    })
  }

  gotoPast = () => {
    let calendarApi = this.calendarComponentRef.current.getApi()
    calendarApi.gotoDate('2000-01-01') // call a method on the Calendar object]
  }


  handleDateClick= (arg) => {
    confirmAlert({
      title: 'Add Event',
      message: 'Would you like to add an event to ' + arg.dateStr + ' ?',
      buttons: [
        {
          label: 'Cancel',
        },
        {
          label: 'Add',
          onClick: () => this.AddEvent()
        }
      ]
    })
  }

  AddEvent =()=>{
    this.props.history.push('/addevent')
  }
  
}

const mapStateToProps = (state) => {
  return{
    events: state.event.events,
    user:state.auth.user,
    isLogin: state.auth.isLogin
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    getEvent: (user_id) => dispatch(getEvent(user_id))
  }
}

export default connect(mapStateToProps,mapDispatchToProps)(Calendar);