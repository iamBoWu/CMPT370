package com.sms.boot.service;

import java.util.List;

/**
 *  Event service layer implement
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sms.boot.mapper.EventMapper;
import com.sms.boot.pojo.Event;

@Service
public class EventService extends ServiceImpl<EventMapper, Event>{

	// Get data from data layer
	@Autowired
	EventMapper eventmapper;
	
	// get the detail for a event by event id
	public Event getEvent(Integer eventId) {
		Event event = eventmapper.queryEvent(eventId);
		return event;
	}
	
	// add a new event to database
	public void addEvent(Event event) {
		eventmapper.addEvent(event);
	}
	
	// update a event
	public void updateEvent(Event event) {
		eventmapper.updateEvent(event);
	}
	
	// delete a event by id
	public void deleteEvent(Integer eventId) {
		eventmapper.deleteById(eventId);
	}
	
	// check event time conflict
	public List<Event> checkTimeConflict(Event event){
		List<Event> events = eventmapper.checkTimeConflict(event);
		return events;
	}
	
}
