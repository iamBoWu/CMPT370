package com.sms.boot.pojo;

/**
 *  Friend subclass
 */
public class FriendForm extends Friend{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String friendName;
	
	private String friendEmail;
	
	private String groupColor;
	
	

	public String getGroupColor() {
		return groupColor;
	}

	public void setGroupColor(String groupColor) {
		this.groupColor = groupColor;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getFriendEmail() {
		return friendEmail;
	}

	public void setFriendEmail(String friendEmail) {
		this.friendEmail = friendEmail;
	}
	
	
}
