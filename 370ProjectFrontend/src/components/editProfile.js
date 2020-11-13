  
import React, { Component } from 'react';
import { connect } from 'react-redux'
import { edit_profile } from '../actions/authActions'
import 'antd/dist/antd.css';
import {  Form, Input, Button } from 'antd';
//Container Component
class editProfile extends Component {
  state = {
    userId:this.props.user.userId,
    userName:'',
    password:'',
    userFirstname:'',
    userLastname:'',
    userEmail:'',
    userAge:'',
    userRole:''
}

handleChange =(e)=>{
  if(e.target.id ==='userAge'){
    console.log(e.target.value)
    this.setState({
      [e.target.id]:parseInt(String(e.target.value))
  })
  }else{this.setState({
    [e.target.id]:e.target.value
})}
  
}

handleSubmit =(e)=>{
  this.props.edit_profile(this.state);
  this.setState({
    userName:'',
    password:'',
    userFirstname:'',
    userLastname:'',
    userEmail:'',
    userAge:'',
    userRole:''
  });
  this.props.history.push('/profile')
}

handleCancel =(e)=>{
  e.preventDefault();
  this.props.history.push('/profile')
}
headerStyle={
  margin: '0px 100px 30px 150px',
}
usernameStyle = {
    border:'1px solid lightgrey',
    width:400,
    height:'50px'
};

userEmailStyle = {
    border:'1px solid lightgrey',
    width:400,
    height:'50px'
}

userFirstnameStyle ={
    border:'1px solid lightgrey',
    width:400,
    height:'50px'
}

userLastnameStyle ={
    border:'1px solid lightgrey',
    width:400,
    height:'50px'
}

passwordStyle = {
    border:'1px solid lightgrey',
    width:400,
    height:'50px'
}

userAgeStyle ={
    border:'1px solid lightgrey',
    width:400,
    height:'50px'
}

userRoleStyle ={
    border:'1px solid lightgrey',
    width:400,
    height:'50px'
}
formStyle ={
  margin: '80px 50px 80px 350px',
}

buttonStyle ={
  margin: '0px 50px 0px 100px',
}
  render() {
    const { authError} = this.props;
    return (
      <div className="editProfile container">
      <Form onFinish={this.handleSubmit} style={this.formStyle}>
      <h5 style={this.headerStyle}>Edit Profile</h5>
          <label htmlFor='userName'>Username</label>
          <div className="input-field">
              <Input 
              type='text' 
              id='userName' 
              onChange={this.handleChange} 
              value={this.state.userName}
              style={this.usernameStyle}
              ></Input>
          </div>
          <label htmlFor='userEmail'>Email</label>
          <div className="input-field">
              <Input 
              type='text' 
              id='userEmail' 
              onChange={this.handleChange} 
              value={this.state.userEmail}
              style={this.userEmailStyle}
              ></Input>
          </div>
          <label htmlFor='userFirstname'>Firstname</label>
          <div className="input-field">
              <Input 
              type='text' 
              id='userFirstname' 
              onChange={this.handleChange} 
              value={this.state.userFirstname}
              style={this.userFirstnameStyle}
              ></Input>
          </div>
          <label htmlFor='userLastname'>Lastname</label>
          <div className="input-field">
              <Input 
              type='text' 
              id='userLastname' 
              onChange={this.handleChange} 
              value={this.state.userLastname}
              style={this.userLastnameStyle}
              ></Input>
          </div>
          <label htmlFor='password'>Password</label>
          <div className="input-field">
              <Input
              type='text' 
              id='password' 
              onChange={this.handleChange} 
              value={this.state.password} 
              style={this.passwordStyle}
              ></Input>
          </div>
          <label htmlFor='userAge'>Age</label>
          <div className="input-field">
              <Input 
              type='text' 
              id='userAge' 
              onChange={this.handleChange} 
              value={this.state.userAge}
              style={this.userAgeStyle}
              ></Input>
          </div>
          <label htmlFor='userRole'>Role</label>
          <div className="input-field">
              <Input 
              type='text' 
              id='userRole' 
              onChange={this.handleChange} 
              value={this.state.userRole}
              style={this.userRoleStyle}
              ></Input>
          </div>
          <div className="input-field">
          <Button type="primary" htmlType="submit" style={this.buttonStyle}>Submit</Button>&nbsp;&nbsp;
          <Button type="primary" htmlType="submit" onClick={this.handleCancel.bind(this)}>Cancel</Button>
              <div className="center red-text">
                  { authError ? <p>{authError}</p> : null }
              </div>
          </div>
      </Form>
</div>
    );
  }
}
const mapStateToProps = (state) => {
  return{
    user: state.auth.user,
    authError: state.auth.authError
  }
  }
  
const mapDispatchToProps = (dispatch) => {
    return {
        edit_profile:(newUser) => dispatch(edit_profile(newUser))
    }
  }

export default connect(mapStateToProps,mapDispatchToProps)(editProfile);
