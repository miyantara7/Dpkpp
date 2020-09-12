package com.app.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.PositionService;
import com.app.service.UnitService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/dpkpp/lov")
public class DpkppLovController {

	@Autowired
	private UnitService unitService;
	
	@Autowired
	private PositionService positionService;
	
	@GetMapping("/unit")
	@Transactional
	public ResponseEntity<?> getAllUnit() throws Exception {
		try {
			return new ResponseEntity<>(unitService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/position")
	@Transactional
	public ResponseEntity<?> getAllPosition() throws Exception {
		try {
			return new ResponseEntity<>(positionService.getAllPosition(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/absent")
	@Transactional
	public ResponseEntity<?> getAllAbsent() throws Exception {
		try {
			return new ResponseEntity<>(positionService.getAllAbsent(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
