import React from 'react'
import { List, Avatar, Row, Col, Button, Select, Typography, Tag, Modal} from 'antd';
import { connect } from 'react-redux'
import 'antd/dist/antd.css';
import axios from 'axios'

const { CheckableTag } = Tag;

const { Option } = Select;

const { Title } = Typography;

const tagsFromServer = ['Default', 'Family', 'Student', 'Teacher', 'Professor','Employee','Employer'];

const apiUrl = 'http://localhost:8080'

class Friend extends React.Component {


    constructor(props){
        super(props);
        this.state = {
            dataSource:[],
            otherUser:[],
            userOption:[],
            selectUser:[],
            selectedTags: "Default",
            selectedTagsInModel: "Default",
            visible: false,
            eventChoose:""
        };
    }

    getFriends = (user_id) =>{
        axios.get(`${apiUrl}/getfirends/${user_id}`)
         .then(response=>{
            this.setState({dataSource:response.data});
            console.log(response.data);
            this.getOtherUsers(this.props.user.userId);
         })
         .catch(erro =>{
         })
    }

    getOtherUsers = (user_id) =>{
      axios.get(`${apiUrl}/getotherusers/${user_id}`)
       .then(response=>{
          let options = [];
          for (let i = 0; i < response.data.length; i++) {
            const value = response.data[i].userName;
            const email = response.data[i].userEmail;
            const id = response.data[i].userId;
            options.push(<Option key={id}>{value+" "+email}</Option>);
          }
          this.setState({dataSource:this.state.dataSource,otherUser:response.data,userOption:options});
          console.log(this.state);
       })
       .catch(erro =>{
       })
   }

   addFriend = (friend) =>{
      axios.post(`${apiUrl}/addfriend`,friend)
      .then(response=>{
        this.getFriends(this.props.user.userId);
     })
     .catch(erro =>{
     })
   }

    componentDidMount() {
        this.getFriends(this.props.user.userId);
      }

    handleChange = (value) =>{
      this.setState({
        selectUser: value,
      });
    }

    handleAddFriends = (value) =>{
        for (let i = 0; i < value.length; i++) {
          let friend = {userId: this.props.user.userId, friendId: parseInt(value[i]), friendGroup: this.state.selectedTags}
          this.addFriend(friend);
        }
        this.clearSelected();
    }

    clearSelected() {
      this.setState({
        selectUser: [],
      });
    }
    
    handleDelete = (value) =>{
      let id = value.tbFriendId;
      axios.delete(`${apiUrl}/deleteFriend/${id}`)
      .then(()=>{
        this.getFriends(this.props.user.userId);
       
      }).catch(err=>{

      })
    }

    handleTagChange (tag) {
      // const { selectedTags } = this.state;
      // const nextSelectedTags = checked ? [...selectedTags, tag] : selectedTags.filter(t => t !== tag);
      // console.log('You are interested in: ', tag);
      this.setState({ selectedTags: tag });
      // console.log(this.state)
    }

    handleTagChangeInModel (tag){
      this.setState({ selectedTagsInModel: tag });
    }

    handleTagUpdate (id) {
      this.setState({
        visible: true,
        eventChoose: id
      });
    } 

    handleCancel = e => {
      console.log(e);
      this.setState({
        visible: false,
        selectedTagsInModel: "Default"
      });
    };

    updateGroup =() =>{
      axios.put(`${apiUrl}/updateFriend`, {tbFriendId: this.state.eventChoose, friendGroup: this.state.selectedTagsInModel})
      .then(response=>{
        this.getFriends(this.props.user.userId);
        this.setState({
          visible: false,
          selectedTagsInModel: "Default",
        });
     })
     .catch(erro =>{
     })
    }
  
      
    render(){
        const { selectedTags } = this.state;
        return (
          <div>
          <>
          <Row style={{marginBottom:"10px"}}>
          <Col className="gutter-row"  span={12} offset={6}>
              <Title level={4}>Add Friends</Title>
              <Select
              mode="multiple"
              style={{ width: '100%' }}
              placeholder="Please select"
              value={this.state.selectUser}
              onChange={this.handleChange}
              
            >
              {this.state.userOption}
            </Select>
            </Col>
          </Row>

          <Row style={{marginBottom:"10px"}}>
            <Col span={12} offset={6}>
            <span style={{ marginRight: 8 }}>Groups:</span>
            {tagsFromServer.map(tag => (
              <CheckableTag
                key={tag}
                checked={selectedTags.indexOf(tag) > -1}
                onChange={checked => this.handleTagChange(tag)}
              >
                {tag}
              </CheckableTag>
            ))}
            </Col>
          </Row>


          <Row style={{marginBottom:"10px"}}>
          <Col span={12} offset={6}>
            <Button type="primary" onClick={this.handleAddFriends.bind(this,this.state.selectUser)}>ADD</Button>
          </Col>
          </Row>
          
         

            <Row>
              <Col span={12} offset={6}>
              <Title level={4}>Friends</Title>
              <List
              itemLayout="horizontal"
              dataSource={this.state.dataSource}
              size="small"
              renderItem={item => (
              <List.Item actions={[<Tag color={item.groupColor} onClick={this.handleTagUpdate.bind(this,item.tbFriendId)}>{item.friendGroup}</Tag>,<Button type="danger" onClick={this.handleDelete.bind(this,item)}>Delete</Button>]}>
                  
                  <List.Item.Meta
                    avatar={<Avatar src="https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png" />}
                    title={item.friendName}
                    description={item.friendEmail}
                  />
                </List.Item>
              )}
            />
            </Col>
          </Row>
          </>

          
          <Modal
          title="Choose a new group"
          visible={this.state.visible}
          onOk={this.updateGroup}
          onCancel={this.handleCancel}
          okText="Update"
          width="600px"
          >
            <span style={{ marginRight: 8 }}>Groups:</span>
            {tagsFromServer.map(tag => (
              <CheckableTag
                key={tag}
                checked={this.state.selectedTagsInModel.indexOf(tag) > -1}
                onChange={checked => this.handleTagChangeInModel(tag)}
              >
                {tag}
              </CheckableTag>
            ))}
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
    }
  }

export default connect(mapStateToProps,mapDispatchToProps)(Friend)

