package com.sms.boot.controller;

/**
 *  Friend Interface
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sms.boot.pojo.Friend;
import com.sms.boot.pojo.FriendForm;
import com.sms.boot.pojo.User;
import com.sms.boot.service.FriendService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FriendController {
	
	// Get data from service layer
	@Autowired
	FriendService friendService;
	
	// get all friends by user id
	@GetMapping("/getfirends/{id}")
	public Object getFriends(@PathVariable("id") Integer userId) {
		List<FriendForm> friends = friendService.getAllFriends(userId);
		return friends;
	}
	
	// get other users who are not user's friends by user id
	@GetMapping("/getotherusers/{id}")
	public Object getOtherUsers(@PathVariable("id") Integer userId) {
		List<User> users = friendService.getOtherUsers(userId);
		return users;
	}
	
	// get all friends include their information by user id
	@GetMapping("/getfriendswithinfo/{id}")
	public Object getFriendsWithInfo(@PathVariable("id") Integer userId) {
		List<User> friends = friendService.queryFriendsWithInfo(userId);
		return friends;
	}
	
	// get all friends in a specific group
	@PostMapping("/getGroupMember")
	public List<Friend> getGroupMember(@RequestBody Friend friend) {
		List<Friend> friends = friendService.queryGroupMember(friend);
		return friends;
	}
	
	// add a friend 
	@PostMapping("/addfriend")
	public void addFriend(@RequestBody Friend friend) {
		friendService.addFriend(friend);
	}
	
	// delete a friend by friend id
	@DeleteMapping("/deleteFriend/{id}")
	public void deleteFriend(@PathVariable("id") Integer id) {
		friendService.deleteFriend(id);
	}
	
	// update a friend's group
	@PutMapping("/updateFriend")
	public void updateFriend(@RequestBody Friend friend) {
		friendService.updateFriend(friend);
	}
}
