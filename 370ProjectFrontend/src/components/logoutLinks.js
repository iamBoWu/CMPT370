import React from 'react'
import { NavLink } from 'react-router-dom'

const LogoutLinks = () => {
  return (
    <div>
      <ul className="right">
        <li><NavLink to='/signup'>Signup</NavLink></li>
        <li><NavLink to='/login'>Login</NavLink></li>
      </ul>
    </div>
  )
}

export default LogoutLinks