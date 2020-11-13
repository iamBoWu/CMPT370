package com.sms.boot.service;

/**
 *  Friend service layer implement
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sms.boot.mapper.FriendMapper;
import com.sms.boot.pojo.Friend;
import com.sms.boot.pojo.FriendForm;
import com.sms.boot.pojo.User;

@Service
public class FriendService extends ServiceImpl<FriendMapper, Friend>{
	
	// Get data from data layer
	@Autowired
	FriendMapper friendmapper;
	
	// get all friends by user id
	public List<FriendForm> getAllFriends(Integer userId){
		List<FriendForm> friends = friendmapper.queryAllFriends(userId);
		return friends;
		
	}
	
	// get other users who are not user's friends by user id
	public List<User> getOtherUsers(Integer userId){
		List<User> users = friendmapper.queryOtherUsers(userId);
		return users;
	}
	
	// add a friend 
	public void addFriend(Friend friend) {
		friendmapper.addFriend(friend);
	}
	
	// delete a friend by friend id
	public void deleteFriend(Integer id) {
		friendmapper.deleteById(id);
	}
	
	// get all friends include their information by user id
	public List<User> queryFriendsWithInfo(Integer userId){
		List<User> friends = friendmapper.queryFriendsWithInfo(userId);
		return friends;
		
	}
	
	// update a friend's group
	public void updateFriend(Friend friend) {
		friendmapper.updateFriend(friend);
	}
	
	// get all friends in a specific group
	public List<Friend> queryGroupMember(Friend friend){
		List<Friend> friends = friendmapper.queryGroupMember(friend);
		return friends;
	}

}
