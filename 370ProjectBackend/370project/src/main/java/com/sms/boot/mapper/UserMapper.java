package com.sms.boot.mapper;

/**
 *  User data layer interface
 */
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sms.boot.pojo.User;

@Mapper
public interface UserMapper extends BaseMapper<User>{
	
	// login with username and password
	User queryUser(@Param("u")User user);
	
	// sign up a new user
	void addUser(@Param("u")User user);
	
	// update user
	 void updateUser(@Param("u")User user);
}
