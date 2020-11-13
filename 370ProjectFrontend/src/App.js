  
import React, { Component } from 'react'
import Navbar from './components/navbar'
import { Route, Switch, BrowserRouter } from 'react-router-dom'
// import Home from './components/home'
import Login from './components/login'
import Signup from './components/signup'
import Profile from './components/profile'
import editProfile from './components/editProfile'
import myEvents from './components/myEvents'
import eventDetail from './components/eventDetail'
import AddEvent from './components/addEvent'
import Calendar from './components/calendar'
import { connect } from 'react-redux'
import editEvent from './components/editEvent'
import Friend from './components/friend'
import Pending from './components/pending'
import Home from './components/home'
// Todo: switch home page
// If user loged in, show him calendar
// If user does not log in, show him the Introduction page
class App extends Component {
  
  
  render() {

    // const { auth } = this.props;
    // const Homes = (auth.isLogin === 'yes') ? <Route exact path='/' component={Home}/> : <Route path='/login' component={Login}/>;
    return (
      <BrowserRouter>
        <div className="App">
          <Navbar />
          <Switch>
          <Route exact path='/' component={Calendar}/>
          <Route path='/home' component={Home}/>
          <Route path='/myevents/:eventId' component={eventDetail}/>
          <Route path='/editevent/:eventId' component={editEvent}/>
          <Route path='/login' component={Login}/>
          <Route path='/signup' component={Signup}/>
          <Route path='/profile' component={Profile}/>
          <Route path='/editprofile' component={editProfile}/>
          <Route path='/myevents' component={myEvents}/>
          <Route path='/addevent' component={AddEvent}/>
          <Route path='/myfriends' component={Friend}/>
          <Route path='/eventpengding' component={Pending}/>
          </Switch>
        </div>
        
      </BrowserRouter>
    );
  }
}


const mapStateToProps = (state) => {
  return{
    auth: state.auth
  }
}

export default connect(mapStateToProps)(App);