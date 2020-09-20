package com.app.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Login;
import com.app.service.LoginService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/api")
public class LoginController {

	@Autowired
	private LoginService userDetailService;

	@PostMapping("/login")
	@Transactional
	public ResponseEntity<?> loginWeb(@RequestBody Login login) throws Exception {
		try {
			return ResponseEntity.ok(userDetailService.loginWeb(login));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login-mobile")
	@Transactional
	public ResponseEntity<?> loginMobile(@RequestBody Login login) throws Exception {
		try {
			return ResponseEntity.ok(userDetailService.loginMobile(login));
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	
	@PostMapping("/logout-mobile")
	@Transactional
	public ResponseEntity<?> logOutMobile(@RequestBody Login login) throws Exception {
		try {
			userDetailService.logOutMobile(login);
			return new ResponseEntity<>("Success",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/logout")
	@Transactional
	public ResponseEntity<?> logOutWeb(@RequestBody Login login) throws Exception {
		try {
			userDetailService.logOutWeb();
			return new ResponseEntity<>("Success",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}