package com.sms.boot.service;

/**
 *  User service layer implement
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sms.boot.mapper.UserMapper;
import com.sms.boot.pojo.User;

@Service
public class UserService extends ServiceImpl<UserMapper, User>{
	
	// Get data from data layer
	@Autowired
	UserMapper loginMapper;
	
	// login with username and password
	public User getUser(User user) {
		User u = loginMapper.queryUser(user);
		return u;
	}
	
	// sign up a new user
	public void addUser(User user) {
		loginMapper.addUser(user);
	}
	
	// update user
	 public void updateUser(User user) {
	  loginMapper.updateUser(user);
	 }
	
}
