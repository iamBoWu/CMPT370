package com.sms.boot.pojo;

/**
 *  Share table ORM
 */
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

@TableName("share")
public class Share extends Model<User>{
	private static final long serialVersionUID = 1L;
	
	@TableId(value = "share_id", type = IdType.AUTO)
	private Integer shareId;
	
	@TableField("share_user_id")
	private Integer shareUserId;
	
	@TableId(value = "event_id")
	private Integer eventId;
	
	@TableLogic
	@TableField("is_del")
	private Integer isDel;
	
	public Integer getShareId() {
		return shareId;
	}

	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}

	public Integer getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Integer shareUserId) {
		this.shareUserId = shareUserId;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}
