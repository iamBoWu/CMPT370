package com.sms.boot.mapper;

/**
 *  HomePage (Calendar) data layer interface
 */
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sms.boot.pojo.Event;

@Mapper
public interface CalenderMapper extends BaseMapper<Event>{
	
	// Get all events for filling calendar page
	List<Event> queryAllEventsById(Integer userId);
	
	// pattern search by event title
	List<Event> querySearchEvents(@Param("e")Event event);
	
	// sort events by priority
	List<Event> queryAllEventsByPriority(Integer userId);
	
	// sort events by starttime
	List<Event> queryAllEventsByStartTime(Integer userId);
	
	// sort events by endtime
	List<Event> queryAllEventsByEndTime(Integer userId);
}
