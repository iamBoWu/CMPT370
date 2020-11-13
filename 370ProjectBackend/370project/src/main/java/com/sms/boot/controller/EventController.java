package com.sms.boot.controller;

import java.util.List;

/**
 *  Event Interface
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sms.boot.pojo.Event;
import com.sms.boot.service.EventService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

	// Get data from service layer
	@Autowired
	EventService eventservice;
	
	// add a new event to database
	@PostMapping("/addnewevent")
	public Object addEvnet(@RequestBody Event event) {
		List<Event> events = checkTimeConflict(event);
		
		if (events.isEmpty()) {
			eventservice.addEvent(event);
			return "Success";
		}
		System.out.println("Fail");
		return events;
	}
	
	// get the detail for a event by event id
	@GetMapping("/getevent/{id}")
	public Event getEvent(@PathVariable("id") Integer eventId) {
		Event event = eventservice.getEvent(eventId);
		return event;
	}
	
	// update a event
	@PutMapping("/updateEvent")
	public Object updateEvent(@RequestBody Event event) {
		System.out.println(event.getEventId());
		System.out.println(event.getEventTitle());
		System.out.println(event.getNote());
		System.out.println(event.getStartTime());
		System.out.println(event.getEndTime());
		System.out.println(event.getUserId());
		List<Event> events = checkTimeConflict(event);
		
		if (events.isEmpty()) {
			eventservice.updateEvent(event);
			return "Success";
		}
		System.out.println("Fail");
		return events;
	}
	
	// delete a event by id
	@DeleteMapping("/deleteEvent/{id}")
	public void deleteEvent(@PathVariable("id") Integer eventId) {
		eventservice.deleteEvent(eventId);
	}
	
	// check event time conflict
	public List<Event> checkTimeConflict(Event event){
		List<Event> events = eventservice.checkTimeConflict(event);
		return events;
	}
	
}
