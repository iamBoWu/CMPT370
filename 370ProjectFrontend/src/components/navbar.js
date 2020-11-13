import React from 'react'
import {Link} from 'react-router-dom'
import LoginLinks from './loginLinks'
import LogoutLinks from './logoutLinks'
import { connect } from 'react-redux'


const Navbar = (props) => {
  const { auth } = props;

  const links = (auth.isLogin === 'yes') ? <LoginLinks profile={auth.user} /> : <LogoutLinks />;
  
  return (
    <nav className="nav-wrapper blue darken-3">
      <div className="container">
        <Link to="/" className="brand-logo">SaskTech</Link>
        {links}
      </div>
    </nav> 
  )
}

const mapStateToProps = (state) => {
    return{
      auth: state.auth
    }
  }
export default connect(mapStateToProps)(Navbar)
