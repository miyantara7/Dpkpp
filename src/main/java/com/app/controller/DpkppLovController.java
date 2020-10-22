package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.PersonService;
import com.app.service.RoleUserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/dpkpp/lov")
public class DpkppLovController {
	
	@Autowired
	private PersonService petugas;
	
	@Autowired
	private RoleUserService role;
	
	@GetMapping("/petugas")
	public ResponseEntity<?> getPetugas() throws Exception {
		try {
			return new ResponseEntity<>(petugas.getPetugas(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/role")
	public ResponseEntity<?> getRole() throws Exception {
		try {
			return new ResponseEntity<>(role.getRoleList(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
