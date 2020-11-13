package com.sms.boot.service;

/**
 *  Share service layer implement
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sms.boot.mapper.ShareMapper;
import com.sms.boot.pojo.EventForm;
import com.sms.boot.pojo.Share;

@Service
public class ShareService extends ServiceImpl<ShareMapper, Share>{

	// Get data from data layer
	@Autowired
	ShareMapper shareMapper;

	// add a share record
	public void addShare(Share share) {
		shareMapper.addShare(share);
	}

	// get all share events
	public List<EventForm> getAllShareEvents(Integer shareUserId){
		List<EventForm> events = shareMapper.queryAllShareEvents(shareUserId);
		return events;
	}
	
	// delete a share record
	public void deleteShare(Share share) {
		shareMapper.deleteShare(share);
	}
}
