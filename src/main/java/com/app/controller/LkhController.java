package com.app.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.service.LkhService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/lkh")
public class LkhController {

	@Autowired
	private LkhService lkhService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> add(MultipartFile file,String lkh) throws Exception {
		try {
			lkhService.add(file,lkh);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/get-list", params = {"page","limit"})
	public ResponseEntity<?> getListLkh(int page,int limit) throws Exception {
		try {			
			return new ResponseEntity<>(lkhService.getListLkh(page,limit), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/get-list/search", params = {"page","limit"})
	public ResponseEntity<?> getListLkhBySearch(String inquiry,int page,int limit) throws Exception {
		try {			
			return new ResponseEntity<>(lkhService.getListLkhBySearch(inquiry,page,limit), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/get-list-mobile")
	public ResponseEntity<?> getListLkhMobile() throws Exception {
		try {			
			return new ResponseEntity<>(lkhService.getListLkhMobile(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/edit")
	@Transactional
	public ResponseEntity<?> editPerson(MultipartFile file,String lkh) throws Exception {
		try {
			lkhService.edit(file,lkh);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPersonAdmin(@PathVariable("id") String id) throws Exception {
		try {			
			return new ResponseEntity<>(lkhService.getLkhById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
