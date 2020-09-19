package com.app.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.service.PersonService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@PutMapping("/edit")
	@Transactional
	public ResponseEntity<?> editPerson(MultipartFile file,String person) throws Exception {
		try {
			personService.editPerson(file,person);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(params = {"page","limit"})
	public ResponseEntity<?> getAllPersonByPaging(int page,int limit) throws Exception {
		try {			
			return new ResponseEntity<>(personService.getAllPersonByPaging(page,limit), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/search",params = {"page","limit"})
	public ResponseEntity<?> getAllPersonBySearch(int page,int limit,String inquiry) throws Exception {
		try {			
			return new ResponseEntity<>(personService.getAllPersonBySearch(page,limit,inquiry), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> getPerson() throws Exception {
		try {			
			return new ResponseEntity<>(personService.findbyId(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("admin/get-person/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getPersonAdmin(@PathVariable("id") String id) throws Exception {
		try {			
			return new ResponseEntity<>(personService.findbyIdAdmin(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
