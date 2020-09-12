package com.app.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.UnitPosition;
import com.app.pojo.PojoUnitPositionSelector;
import com.app.service.UnitPositionService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/unit-position")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UnitPositionController {
	
	@Autowired
	private UnitPositionService unitPositionService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> add(@RequestBody UnitPosition unitPosition) throws Exception {
		try {
			unitPositionService.add(unitPosition);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/get-unit-position")
	@Transactional
	public ResponseEntity<?> getUnitPositionByBk(@RequestBody PojoUnitPositionSelector unitPosition) throws Exception {
		try {
			return new ResponseEntity<>(unitPositionService.getUnitPositionByBk(unitPosition), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
