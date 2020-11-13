package com.sms.boot.controller;

/**
 *  User Interface
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sms.boot.pojo.User;
import com.sms.boot.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	// Get data from service layer
	@Autowired
	private UserService UserService;
	
	// login with username and password
	@PostMapping(value = "/login")
	public User userLogin(@RequestBody User user) {
		System.out.println(user.getUserName());
		System.out.println(user.getPassword());
		
		User u = UserService.getUser(user);
		
		if (u!= null) {
	        System.out.println("success");
			return u;
		}
		
		return u;
	}
	
	// sign up a new user
	@PostMapping(value = "/signup")
	public User addUser(@RequestBody User user) {
		System.out.println(user.getUserName());
		System.out.println(user.getUserAge());
		UserService.addUser(user);
		
		User u = UserService.getUser(user);
		
		if (u!= null) {
	        System.out.println("success");
			return u;
		}
		
		return u;
	}
	
	// update user information
	 @PostMapping(value = "/editprofile")
	 public User updateUser(@RequestBody User user) {
	   UserService.updateUser(user);
	   
	   User u = UserService.getUser(user);
	   
	   if (u!= null) {
	          System.out.println("success");
	    return u;
	   }
	   
	   return u;
	  }
	
}
