package com.sms.boot.mapper;

/**
 *  Friend data layer interface
 */
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sms.boot.pojo.Friend;
import com.sms.boot.pojo.FriendForm;
import com.sms.boot.pojo.User;

@Mapper
public interface FriendMapper extends BaseMapper<Friend>{
	
	// add a friend 
	void addFriend(@Param("f") Friend friend);
	
	// get all friends by user id
	List<FriendForm> queryAllFriends(Integer userId);
	
	// get other users who are not user's friends by user id
	List<User> queryOtherUsers(Integer userId);
	
	// get all friends include their information by user id
	List<User> queryFriendsWithInfo(Integer userId);
	
	// update a friend's group
	void updateFriend(@Param("f") Friend friend);
	
	// get all friends in a specific group
	List<Friend> queryGroupMember(@Param("f") Friend friend);
	
}
