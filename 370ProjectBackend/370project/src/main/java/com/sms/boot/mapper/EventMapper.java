package com.sms.boot.mapper;

import java.util.List;

/**
 *  Event data layer interface
 */
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sms.boot.pojo.Event;

@Mapper
public interface EventMapper extends BaseMapper<Event>{
	
	// add a new event to database
	void addEvent(@Param("e")Event event);
	
	// get the detail for a event by event id
	Event queryEvent(Integer eventid);
	
	// update a event
	void updateEvent(@Param("e")Event event);
	
	// check event time conflict
	List<Event> checkTimeConflict(@Param("e")Event event);
}
