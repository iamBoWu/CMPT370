import React , { Component } from 'react';
import { connect } from 'react-redux';
import { logout } from '../actions/authActions'
import { Redirect } from 'react-router-dom'

class Profile extends Component {
    state = {
      userName:'',
      password:'',
      userFirstname:'',
      userLastname:'',
      userEmail:'',
      userAge:'',
      userRole:'',
      edit:null
    }

    handleEditButton =()=>{
    //   this.setState({
    //     edit:'yes'
    // })
    this.props.history.push('/editprofile');
    }
    
    handleLogout =()=>{
      this.props.logout();
      this.props.history.push('/login');
    }

    render(){
        const { user } = this.props;
        if (this.state.edit === 'yes') return <Redirect to='/editprofile' /> 
        return (
            <div className="Profile container">
              <div className='center'>
                  <div className="username">
                      <label htmlFor='username' style={this.labelstyle}>User Name:</label>
                      <h5 style={this.h5style}>{user.userName}</h5>
                  </div>
                  
                  <div className="firstname">
                      <label htmlFor='firstname' style={this.labelstyle}>First Name:</label>
                      <h5 style={this.h5style}>{user.userFirstname}</h5>
                  </div>

                  <div className="lastname">
                      <label htmlFor='lastname' style={this.labelstyle}>Last Name:</label>
                      <h5 style={this.h5style}>{user.userLastname}</h5>
                  </div>

                  <div className="email">
                      <label htmlFor='email' style={this.labelstyle}>Email:</label>
                      <h5 style={this.h5style}>{user.userEmail}</h5>
                  </div>
                  <div className="age">
                      <label htmlFor='age' style={this.labelstyle}>Age:</label>
                      <h5 style={this.h5style}>{user.userAge}</h5>
                  </div>
                  <div className="role">
                      <label htmlFor='role' style={this.labelstyle}>Role:</label>
                      <h5 style={this.h5style}>{user.userRole}</h5>
                  </div>
                  <div className='btn1'>
                    <button className="waves-effect waves-light btn-small" onClick={this.handleLogout.bind(this)} style={{background:'#1890ff',float: 'left'}}>Logout</button>
                  </div>
                  <div className='btn2'>
                    <button className="waves-effect waves-light btn-small" onClick={this.handleEditButton.bind(this)} style={{background:'#1890ff',float: 'right'}}>Edit</button>
                  </div>
                
                
              </div>
            </div>
        )
    }
  
  labelstyle = {
    width:'25%',
    float: 'left',
    textAlign: 'left',
    marginTop: '10px',
    padding: '12px 0 12px 0',
    fontSize: '20px'
  }

  h5style = {
    border: '1px solid #ccc',
    borderRadius: '4px',
    width:'75%',
    float: 'left',
    textAlign: 'left',
    marginTop: '10px',
    padding: '12px'
  }
  
}

const mapStateToProps = (state) => {
    return {
      user: state.auth.user
     
    }
  }

const mapDispatchToProps = (dispatch) => {
    return {
        logout : ()=>{dispatch(logout())}
    }
  }
export default connect(mapStateToProps,mapDispatchToProps)(Profile)