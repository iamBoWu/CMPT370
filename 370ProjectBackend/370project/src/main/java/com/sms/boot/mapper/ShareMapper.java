package com.sms.boot.mapper;

/**
 *  Share data layer interface
 */
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sms.boot.pojo.EventForm;
import com.sms.boot.pojo.Share;

@Mapper
public interface ShareMapper extends BaseMapper<Share>{
	
	// add a share record
	void addShare(@Param("s")Share share);
	
	// get all share events
	List<EventForm> queryAllShareEvents(Integer shareUserId);
	
	// delete a share record
	void deleteShare(@Param("s")Share share);
}
