import React from 'react'
import { NavLink } from 'react-router-dom'
import { connect } from 'react-redux'
import { logout } from '../actions/authActions'
import { backhome } from '../actions/eventActions'

const LoginLinks = (props) => {

  return (
    <div>
      <ul className="right">
        <li><NavLink to='/eventpengding'>Event Pending</NavLink></li>
        <li><NavLink to='/myfriends'>My Friends</NavLink></li>
        <li><NavLink to='/myevents'>My Events</NavLink></li>
        <li><NavLink to='/addevent' onClick={props.backhome}>Add New Event</NavLink></li>
        <li><a href='/' onClick={props.logout}>Log Out</a></li>
        <li><NavLink to='/profile' className="btn btn-floating grey lighten-1">{props.user.userName}</NavLink></li>
      </ul>
    </div>
  )
}
  
const mapStateToProps = (state) => {
  return{
    user: state.auth.user,
  }
}

const mapDispatchToProps = (dispatch) => {
    return {
      logout: () => dispatch(logout()),
      backhome: () => dispatch(backhome()),
    }
  }

export default connect(mapStateToProps,mapDispatchToProps)(LoginLinks)