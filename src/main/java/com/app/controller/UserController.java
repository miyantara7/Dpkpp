package com.app.controller;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
		
	@PostMapping("/register")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Transactional
	public ResponseEntity<?> register(String user, MultipartFile file) throws Exception {
		try {
			userService.save(user,file);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@PostMapping("/update-password")
	@Transactional
	public ResponseEntity<?> update(@RequestBody HashMap<String, String> user) throws Exception {
		try {
			userService.updatePassword(user);;
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("")
	public ResponseEntity<?> getUser() throws Exception {
		try {			
			return new ResponseEntity<>(userService.findById(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	@GetMapping("/detail/{id}")	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@Transactional
	public ResponseEntity<?> getUserDeatil(String id) throws Exception {
		try {			
			return new ResponseEntity<>("", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
