package com.app.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.Absent;
import com.app.service.AbsentSevice;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/absent")
public class AbsentController {

	@Autowired
	private AbsentSevice absentService;
	
	@GetMapping(value = "/admin/get-list", params = {"page","limit"})
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAbsentByPaging(int page,int limit) throws Exception {
		try {			
			return new ResponseEntity<>(absentService.getAbsentByPaging(page,limit), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/admin/get-list/search", params = {"page","limit"})
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getAbsentBySearch(int page,int limit,String inquiry) throws Exception {
		try {			
			return new ResponseEntity<>(absentService.getAbsentBySearch(page,limit,inquiry), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/user/get-absent")
	public ResponseEntity<?> getUserAbsentById() throws Exception {
		try {			
			return new ResponseEntity<>(absentService.getUserAbsentById(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/user/get-absent-history", params = {"page","limit"})
	public ResponseEntity<?> getUserAbsentByIdHistory(Integer page,Integer limit) throws Exception {
		try {			
			return new ResponseEntity<>(absentService.getAbsensiHistori(page,limit), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/admin/get-absent-history/{id}", params = {"page","limit"})
	public ResponseEntity<?> getAdminAbsentByIdHistory(@PathVariable("id")String id,Integer page,Integer limit) throws Exception {
		try {			
			return new ResponseEntity<>(absentService.getAbsensiHistoribyAdmin(id,page,limit), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/user-in")
	@Transactional
	public ResponseEntity<?> absentIn(MultipartFile file,String absent) throws Exception {
		try {		
			return new ResponseEntity<>(absentService.absentIn(file,absent), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/get-photo/{id}")
	@Transactional
	public ResponseEntity<?> getPhot(@PathVariable("id") String id) throws Exception {
		try {		
			return new ResponseEntity<>(absentService.getPhotoAbsent(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/user-out")
	@Transactional
	public ResponseEntity<?> absentOut(MultipartFile file,String absent) throws Exception {
		try {		
			return new ResponseEntity<>(absentService.absentOut(file,absent), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/admin/user-in")
	@Transactional
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> verAbsentInByAdmin(@RequestBody List<Absent> absent ) throws Exception {
		try {		
			absentService.adminAbsentIn(absent);
			return new ResponseEntity<>("OK", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
