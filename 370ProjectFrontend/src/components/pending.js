import React, { Component } from 'react';
import { connect } from 'react-redux'
import {Table, Button, message, Popconfirm} from 'antd'
import 'antd/dist/antd.css';
import axios from 'axios'

message.config({
  top: 150,
  duration: 2,
  maxCount: 3,
});

const apiUrl = 'http://localhost:8080'

class Pending extends Component {
  constructor(props){
    super(props);
    this.state = {
        dataSource:[]
    };
    this.columns = [
      {
        title: 'Event Owner',
        dataIndex: 'userName',
        key: 'userName',
      },
      {
        title: 'Event Title',
        dataIndex: 'eventTitle',
        key: 'eventTitle',
      },
      {
        title: 'Priority',
        dataIndex: 'priority',
        key: 'priority',
      },
      {
        title: 'Start Time',
        dataIndex: 'startTime',
        key: 'startTime',
      },
      {
          title: 'End Time',
          dataIndex: 'endTime',
          key: 'endTime',
      },
      {
          title:"Regulation",
          dataIndex:"action",
          key:"action",
          render:(text,record)=>{
              return (
                  <React.Fragment>
                    <Button type="primary"  onClick={this.handleClickAdd.bind(this,record)}>Accept</Button>&nbsp;&nbsp;
                    <Popconfirm
                      title="Are you sure reject this event?"
                      onConfirm={this.handleClickDelete.bind(this,record.eventId)}
                      onCancel={this.cancel}
                      okText="Yes"
                      cancelText="No"
                    >
                    <Button type="emergency">Reject</Button>
                    </Popconfirm>,
                  </React.Fragment>
              )
          }
      },
    ];
  }

  cancel(e) {
    console.log(e);
  }


  handleClickDelete = (eventId) => {
      axios.put(`${apiUrl}/deleteShare`, {
        shareUserId: this.props.user.userId,
        eventId: eventId
      })
      .then(response=>{
         this.getshare(this.props.user.userId);
      }).catch(err=>{

      })
  }

  handleClickAdd = (event) => {
        axios.post(`${apiUrl}/addnewevent`,{
          userId: this.props.user.userId,
          eventTitle: event.eventTitle,
          note: event.note,
          priority: event.priority,
          startTime: event.startTime,
          endTime: event.endTime
      })
        .then(response=>{
          let data = response.data;
          if (data==="Success"){
            this.handleClickDelete(event.eventId);
          }
          else{
              this.timeConflict(data);
          }
       })
        .catch(erro =>{

       })
  }

  getshare = (user_id) =>{
    axios.get(`${apiUrl}/showAllShareEvent/${user_id}`)
     .then(response=>{
         this.setState({dataSource:response.data});
     })
     .catch(erro =>{

     })
}

  componentDidMount() {
    this.getshare(this.props.user.userId);
  }

  timeConflict = (conflictEvents) =>{
    let events = ""
    for (let i = 0; i < conflictEvents.length; i++) {
      events = events + conflictEvents[i].eventTitle + " ";
    }
    message.warning('event has the time conflict with ' + events);
  }

  render(){
    return (
      <div>
        <Table rowKey="eventId" columns={this.columns} dataSource={this.state.dataSource} />
      </div>
    )
  }
}

const mapStateToProps = (state) => {
    return {
      user:state.auth.user
    }
  }

const mapDispatchToProps = (dispatch) => {
    return {
    }
  }

  export default connect(mapStateToProps,mapDispatchToProps)(Pending);