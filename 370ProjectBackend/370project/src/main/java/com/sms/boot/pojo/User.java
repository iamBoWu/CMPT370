package com.sms.boot.pojo;

/**
 *  User table ORM
 */
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

@TableName("user")
public class User extends Model<User> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId(value = "user_id", type = IdType.AUTO)
	private Integer userId;
	
	@TableField("user_name")
	private String userName;
	
	@TableField("password")
	private String password;
	
	@TableField("user_firstname")
	private String userFirstname;
	
	@TableField("user_lastname")
	private String userLastname;
	
	@TableField("user_email")
	private String userEmail;
	
	@TableField("user_age")
	private Integer userAge;
	
	@TableField("user_role")
	private String userRole;
	
	@TableLogic
	@TableField("is_del")
	private Integer isDel;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserFirstname() {
		return userFirstname;
	}

	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
}