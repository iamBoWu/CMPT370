import React , { Component } from 'react';
import {Table, Button, Modal, Select, Input, Form, Popconfirm} from 'antd'
import {SearchOutlined} from '@ant-design/icons'
import { connect } from 'react-redux'
import 'antd/dist/antd.css';
import {getEvent} from '../actions/eventActions'
import axios from 'axios'

const { Option } = Select;


const apiUrl = 'http://localhost:8080'

const groups = ['Default', 'Family', 'Student', 'Teacher', 'Professor','Employee','Employer'];


class myEvents extends Component {
    constructor(props){
        super(props);
        this.routePush = this.routePush.bind(this);
        this.state = {
            dataSource:[],
            visible: false,
            visibleGroup: false,
            friends:[],
            selectUser:[],
            selectGroup:[],
            searchContent:'',
            groupOption:[],
            eventChoose:"",
            sortOption:''
        };
        this.columns = [
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
                      <Button type="primary" onClick={this.showModal.bind(this,record.eventId)}>Share(Friends)</Button>&nbsp;&nbsp;
                      <Button type="primary" onClick={this.showModalGroup.bind(this,record.eventId)}>Share(Groups)</Button>&nbsp;&nbsp;
                      <Button type="primary" onClick={this.routePush.bind(this,record.eventId)}>Check</Button>&nbsp;&nbsp;
                      <Popconfirm
                      title="Are you sure delete this event?"
                      onConfirm={this.handleDelete.bind(this,record.eventId)}
                      onCancel={this.cancel}
                      okText="Yes"
                      cancelText="No"
                    >
                      <Button type="primary">Delete</Button>
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

///////////////////////////////////

        handleShare = () => {
          let value = this.state.selectUser;
          for (let i = 0; i < value.length; i++) {
            let share = {eventId: this.state.eventChoose, shareUserId: parseInt(value[i])} 
            this.addShare(share);
          }
          this.setState({
            visible: false,
          });
        };


        addShare = (share) =>{
          axios.post(`${apiUrl}/addNewShare`,share)
          .then(response=>{

        })
        .catch(erro =>{
        })
        }

        shareEvent =(eventId)=>{
          axios.delete(`${apiUrl}/deleteEvent/${eventId}`)
          .then(()=>{
            this.getEvent(this.props.user.userId);
           
          }).catch(err=>{

          })
       }

       showModal = (eventId) => { 
        this.setState({
          visible: true,
          eventChoose: eventId
        });
      };
    
      handleCancel = e => {
        console.log(e);
        this.setState({
          visible: false,
        });
      };

      handleChange = (value) =>{
        this.setState({
          selectUser: value,
        });
      }

      getFriends = (user_id) =>{
        axios.get(`${apiUrl}/getfriendswithinfo/${user_id}`)
         .then(response=>{
          let options = [];
          for (let i = 0; i < response.data.length; i++) {
            const value = response.data[i].userName;
            const email = response.data[i].userEmail;
            const id = response.data[i].userId;
            options.push(<Option key={id}>{value+" "+email}</Option>);
          }
            this.setState({friends:options});
         })
         .catch(erro =>{
         })
    }

////////////////////////////////////// choose groups

makeGroup = () =>{
  let options = [];
  for (let i = 0; i < groups.length; i++) {
    let value = groups[i];
    options.push(<Option key={value}>{value}</Option>);
  }
  this.setState({groupOption:options});
}


handleCancelGroup = e => {
  console.log(e);
  this.setState({
    visibleGroup: false,
  });
  this.clearSelected();
};


showModalGroup = (eventId) => { 
  this.setState({
    visibleGroup: true,
    eventChoose: eventId
  });
};

handleShareGroup = () => {
  let value = this.state.selectGroup;
  for (let i = 0; i < value.length; i++) {
    let group = {friendGroup: value[i], userId: this.props.user.userId};
    this.getGroupMember(group);
  }
  this.setState({
    visibleGroup: false,
  });
  this.clearSelected();

};


getGroupMember = (group) =>{
  axios.post(`${apiUrl}/getGroupMember`, group)
  .then(response=>{
    for (let i = 0; i < response.data.length; i++) {
      let share = {eventId: this.state.eventChoose, shareUserId: response.data[i].friendId} 
      this.addShare(share);
    }
})
.catch(erro =>{
})
}



handleChangeGroup = (value) =>{
  this.setState({
    selectGroup: value,
  });
}

clearSelected() {
  this.setState({
    selectUser: [],
    selectGroup: [],
  });
}


////////////////////////////////////////

      getAllEvent = (user_id) =>{
             axios.get(`${apiUrl}/showAllevent/${user_id}`)
              .then(response=>{
                  console.log(response.data)
                  this.setState({dataSource:response.data});
              })
              .catch(erro =>{
              })
      }


      deleteEvent =(eventId)=>{
            axios.delete(`${apiUrl}/deleteEvent/${eventId}`)
            .then(()=>{
              this.getAllEvent(this.props.user.userId);
               //We need to update state in store also
              this.props.getEvent(this.props.user.userId);
            }).catch(err=>{

            })
            
          
    }

    handleDelete = (eventId) =>{
      this.deleteEvent(eventId);

      }
  
    routePush = (eventId) =>{
      this.props.history.push('/myevents/' + eventId)
    }


    //////////////////////////////////////

    handleSearchChange =(e)=>{
      this.setState({
        ...this.state,
        searchContent:e.target.value
      })
    }

    handleSearch =() =>{
      var serarchState ={
        eventId: null,
        userId: this.props.user.userId,
        eventTitle: this.state.searchContent,
        note: "",
        priority: null,
        startTime: "",
        endTime: "",
        isDel: 0,
      }
      axios.post(`${apiUrl}/showSearchEvent`,serarchState)
              .then(response=>{
                  this.setState({dataSource:response.data});
              })
              .catch(erro =>{
              })
      this.setState({
            ...this.state,
            searchContent:''
          })
    }


    divStyle = {
          border:'1px solid lightgrey',
          margin:'20px 5px 20px 200px',
          float:'left',
          width:200,
          height:'30px'
      };

    inputStyle ={
      padding:'0px 0px 0px 0px',
      width:200,
      height:'30px'
    }
    buttonStyle ={
          margin:'20px',
          float:'left'
    }

     //////////////////////////////////////

     onSelectedChange=(value)=>{
      this.setState({
        ...this.state,
        sortOption:value
      })
     }

     handleSelect =()=>{
      if(this.state.sortOption === 'sortbypriority'){
        axios.get(`${apiUrl}/sortbypriority/${this.props.user.userId}`)
              .then(response=>{
                  console.log(response.data)
                  this.setState({dataSource:response.data});
              })
              .catch(erro =>{
              })
      }
      else if(this.state.sortOption === 'sortbystarttime'){
        axios.get(`${apiUrl}/sortbystarttime/${this.props.user.userId}`)
              .then(response=>{
                  console.log(response.data)
                  this.setState({dataSource:response.data});
              })
              .catch(erro =>{
              })
      }
      else if(this.state.sortOption === 'sortbyendtime'){
        axios.get(`${apiUrl}/sortbyendtime/${this.props.user.userId}`)
              .then(response=>{
                  console.log(response.data)
                  this.setState({dataSource:response.data});
              })
              .catch(erro =>{
              })
      }
     }


    selectStyle ={
      margin:'0px 20px 20px 100px',
      float:'left',
      width:200
    }

    button2Style ={
      float:'left'
    }
    //////////////////////////////////////


    //////////////////////////////////////
    componentDidMount() {
      console.log('componentDidMount')
      this.getAllEvent(this.props.user.userId);
      this.props.getEvent(this.props.user.userId);
      this.getFriends(this.props.user.userId);
      this.makeGroup();
    }

    
    render(){
        return (
            <div>
                <Form onFinish={this.handleSearch.bind(this)}>
                  <div className="input-field" style={this.divStyle}>
                       <Input type='text' 
                       id='searchContent' 
                       style={this.inputStyle} 
                       onChange={this.handleSearchChange} 
                       value={this.state.searchContent}></Input>
                  </div>&nbsp;&nbsp;
                  <Button type="primary" htmlType="submit" icon={<SearchOutlined />} style={this.buttonStyle}>Search</Button>
                </Form>


                <Form onFinish={this.handleSelect.bind(this)}>
                    <Select
                        showSearch
                        style={this.selectStyle}
                        placeholder="Sort Options"
                        optionFilterProp="children"
                        onChange={this.onSelectedChange}
                        filterOption={(input, option) =>
                          option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                        }
                      >
                        <Option value="sortbypriority">Sort By Priority</Option>
                        <Option value="sortbystarttime">Sort By Start Time</Option>
                        <Option value="sortbyendtime">Sort By End Time</Option>
                    </Select>&nbsp;&nbsp;
                    <Button type="primary" htmlType="submit" icon={<SearchOutlined />} style={this.button2Style}>Sort</Button>
                </Form>

                <Table rowKey="eventId" columns={this.columns} dataSource={this.state.dataSource} />

                <Modal
                  title="Choose Friends to Share"
                  visible={this.state.visible}
                  onOk={this.handleShare}
                  onCancel={this.handleCancel}
                  okText="Share"
                >

                            <Select
                            mode="multiple"
                            style={{ width: '100%' }}
                            placeholder="Please select"
                            value={this.state.selectUser}
                            onChange={this.handleChange}
                            
                          >
                            {this.state.friends}
                          </Select>
                    

                </Modal>

                <Modal
                  title="Choose groups to Share"
                  visible={this.state.visibleGroup}
                  onOk={this.handleShareGroup}
                  onCancel={this.handleCancelGroup}
                  okText="Share"
                >
                            <Select
                            mode="multiple"
                            style={{ width: '100%' }}
                            placeholder="Please select"
                            value={this.state.selectGroup}
                            onChange={this.handleChangeGroup}
                            
                          >

                            {this.state.groupOption}
                          </Select>
                </Modal>
              </div>
            
            
        )
    }



}

const mapStateToProps = (state) => {
    return{
      events: state.event.events,
      user:state.auth.user
    }
  }

const mapDispatchToProps = (dispatch) => {
    return {
      getEvent: (user_id) => dispatch(getEvent(user_id))
    }
  }
  

export default connect(mapStateToProps,mapDispatchToProps)(myEvents)