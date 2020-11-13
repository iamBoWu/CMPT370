package com.sms.boot.controller;

/**
 *  HomePage (Calendar) Interface
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sms.boot.pojo.Event;
import com.sms.boot.service.CalenderService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CalenderController {
	
	// Get data from service layer
	@Autowired
	CalenderService calenderService;
	
	// Get all events for filling calendar page
	@GetMapping(value = "/showAllevent/{id}")
	public List<Event> getAllEvent(@PathVariable("id") Integer userId){
		List<Event> events = calenderService.getAllEventsById(userId);
		return events;
	}
	
	// pattern search by event title
	@PostMapping("/showSearchEvent")
	public List<Event> getSearchEvents(@RequestBody Event event) {
		System.out.println("getSearchEvent");
		List<Event> events = calenderService.getSearchEvents(event);
		
		System.out.println(event.getStartTime());
		return events;
	}
	
	// sort events by priority
	@GetMapping(value = "/sortbypriority/{id}")
	public List<Event> getEventsByPriority(@PathVariable("id") Integer userId){
		List<Event> events = calenderService.getAllEventsByPriority(userId);
		return events;
	}
	
	// sort events by starttime
	@GetMapping(value = "/sortbystarttime/{id}")
	public List<Event> getEventsByStartTime(@PathVariable("id") Integer userId){
		List<Event> events = calenderService.getAllEventsByStartTime(userId);
		return events;
	}
	
	// sort events by endtime
	@GetMapping(value = "/sortbyendtime/{id}")
	public List<Event> getEventsByEndTime(@PathVariable("id") Integer userId){
		List<Event> events = calenderService.getAllEventsByEndTime(userId);
		return events;
	}
	
	
}
