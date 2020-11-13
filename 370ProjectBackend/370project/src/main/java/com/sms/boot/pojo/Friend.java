package com.sms.boot.pojo;

/**
 *  Friend table ORM
 */
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

@TableName("friend")
public class Friend extends Model<Friend> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId(value = "tb_friend_id", type = IdType.AUTO)
	private Integer tbFriendId;
	
	@TableField("user_id")
	private Integer userId;
	
	@TableField("friend_id")
	private Integer friendId;
	
	@TableField("friend_group")
	private String friendGroup;

	@TableLogic
	@TableField("is_del")
	private Integer isDel;

	public Integer getTbFriendId() {
		return tbFriendId;
	}

	public void setTbFriendId(Integer tbFriendId) {
		this.tbFriendId = tbFriendId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFriendId() {
		return friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}

	public String getFriendGroup() {
		return friendGroup;
	}

	public void setFriendGroup(String friendGroup) {
		this.friendGroup = friendGroup;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	
}
