package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.NotificationService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/notif")
public class NotificationController {
	
	
	@Autowired
	private NotificationService notService;
	
	
	@GetMapping("")
	public ResponseEntity<?> getNotif() throws Exception {
		try {
			return new ResponseEntity<>(notService.getNotif(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping("/read/{id}")
	public ResponseEntity<?> updateNotif(@PathVariable("id") String id) throws Exception {
		try {
			notService.updateNotifisRead(id);
			return new ResponseEntity<>("Notif Read", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
