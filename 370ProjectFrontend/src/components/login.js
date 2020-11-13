  
import React, { Component } from 'react';
//import {Link} from 'react-router-dom'
import { connect } from 'react-redux'
import { login } from '../actions/authActions'
import { Redirect } from 'react-router-dom'
import 'antd/dist/antd.css';
import {  Form, Input, Button } from 'antd';

//Container Component
class Login extends Component {
    state = {
        userName:'',
        password:''
    }
  
    handleChange =(e)=>{
      this.setState({
          [e.target.id]:e.target.value
      })
    }
  
    handleSubmit =(e)=>{
      this.props.login(this.state)
      this.setState({
        userName:'',
        password:''
      })
    }

    handleSignup =(e)=>{
      e.preventDefault();
      this.props.history.push('/signup')
    }

    headerStyle={
      margin: '0px 100px 30px 150px',
    }
    usernameStyle = {
        border:'1px solid lightgrey',
        width:400,
        height:'50px'
    };

    passwordStyle = {
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
    const { authError , isLogin} = this.props; 
    if (isLogin === 'yes') return <Redirect to='/' /> 
    return (
        <div className="Login container">
            
            <Form onFinish={this.handleSubmit} style={this.formStyle}>
            <h5 style={this.headerStyle}>Login</h5>
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
                <label htmlFor='password'>Password</label>
                <div className="input-field">
                    <Input.Password 
                    type='text' 
                    id='password' 
                    onChange={this.handleChange} 
                    value={this.state.password} 
                    style={this.passwordStyle}
                    ></Input.Password>
                </div>
                <div className="input-field">
                <Button type="primary" htmlType="submit" style={this.buttonStyle}>Login</Button>&nbsp;&nbsp;
                <Button type="primary" htmlType="submit" onClick={this.handleSignup.bind(this)}>Signup</Button>
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
      authError: state.auth.authError,
      isLogin: state.auth.isLogin
    }
  }
  
const mapDispatchToProps = (dispatch) => {
    return {
      login:(credentials) => dispatch(login(credentials))
    }
  }
  
export default connect(mapStateToProps,mapDispatchToProps)(Login);


  
