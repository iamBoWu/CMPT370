package com.sms.boot.service;

/**
 *  HomePage (Calendar) service layer implement
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sms.boot.mapper.CalenderMapper;
import com.sms.boot.pojo.Event;

@Service
public class CalenderService extends ServiceImpl<CalenderMapper, Event>{
	
	// Get data from data layer
	@Autowired
	CalenderMapper calendermapper;
	
	// Get all events for filling calendar page
	public List<Event> getAllEventsById(Integer id){
		List<Event> events = calendermapper.queryAllEventsById(id);
		return events;
	}
	
	// pattern search by event title
	public List<Event> getSearchEvents(Event event){
		List<Event> events = calendermapper.querySearchEvents(event);
		return events;
	}
	
	// sort events by priority
	public List<Event> getAllEventsByPriority(Integer id){
		List<Event> events = calendermapper.queryAllEventsByPriority(id);
		return events;
	}
	
	// sort events by starttime
	public List<Event> getAllEventsByStartTime(Integer id){
		List<Event> events = calendermapper.queryAllEventsByStartTime(id);
		return events;
	}
	
	// sort events by endtime
	public List<Event> getAllEventsByEndTime(Integer id){
		List<Event> events = calendermapper.queryAllEventsByEndTime(id);
		return events;
	}
	
}
