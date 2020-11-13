package com.sms.boot.controller;

/**
 *  Share Interface
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sms.boot.pojo.EventForm;
import com.sms.boot.pojo.Share;
import com.sms.boot.service.ShareService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ShareController {

	// Get data from service layer
	@Autowired
	private ShareService shareService;
	
	// add a share record
	@PostMapping("/addNewShare")
	public void addShare(@RequestBody Share share) {
		shareService.addShare(share);
	}
	
	// get all share events
	@GetMapping(value = "/showAllShareEvent/{id}")
	public List<EventForm> getAllEvent(@PathVariable("id") Integer shareUserId){
		List<EventForm> events = shareService.getAllShareEvents(shareUserId);
		return events;
	}
	
	// delete a share record
	@PutMapping("/deleteShare")
	public void deleteEvent(@RequestBody Share share) {
		shareService.deleteShare(share);
	}
}
