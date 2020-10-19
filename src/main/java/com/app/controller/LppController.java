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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.service.LppService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/lpp")
public class LppController {

	@Autowired
	private LppService lppService;

	@PostMapping
	@Transactional
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> add(MultipartFile file, String lpp) throws Exception {
		try {
			lppService.add(file, lpp);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/verification/personlpp/{id}")
	@Transactional
	@PreAuthorize("hasAuthority('ROLE_VERIFICATOR')")
	public ResponseEntity<?> verificationPersonLpp(@PathVariable("id") String id) throws Exception {
		try {
			lppService.verificationPersonLpp(id);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/verification/progress-laporan/{id}")
	@Transactional
	@PreAuthorize("hasAuthority('ROLE_VERIFICATOR')")
	public ResponseEntity<?> verificationProgressPerson(@PathVariable("id") String id) throws Exception {
		try {
			lppService.verificationProgress(id);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/get")
	public ResponseEntity<?> getListLppByPersonId() throws Exception {
		try {
			return new ResponseEntity<>(lppService.getLppByPersonId(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getListLppByPersonIdAdmin(@PathVariable("id") String id) throws Exception {
		try {
			return new ResponseEntity<>(lppService.getLppByPersonIdAdmin(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/get-all")
	@PreAuthorize("hasAuthority('ROLE_VERIFICATOR')")
	public ResponseEntity<?> getLppByVerificator() throws Exception {
		try {
			return new ResponseEntity<>(lppService.getLppByVerificator(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/admin/{id}")
	public ResponseEntity<?> getListLppAdminDetail(@PathVariable("id") String id) throws Exception {
		try {
			return new ResponseEntity<>(lppService.getAdminDetail(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path = "admin",params = {"page","limit"})
	public ResponseEntity<?> getListLppAdmin(int page,int limit) throws Exception {
		try {
			return new ResponseEntity<>(lppService.getLppbyAdmin(page, limit), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/details/{id}")
	public ResponseEntity<?> getListLppByPersonId(@PathVariable("id") String id) throws Exception {
		try {
			return new ResponseEntity<>(lppService.getProgressLppById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/progress-lpp/details/{id}")
	public ResponseEntity<?> getDetailsLaporanById(@PathVariable("id") String id) throws Exception {
		try {
			return new ResponseEntity<>(lppService.getDetailsLaporanById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/progress-lpp/details/upload-foto/{id}")
	@Transactional
	public ResponseEntity<?> uploadFotoLaporan(@PathVariable("id") String id, MultipartFile depan,
			MultipartFile samping, MultipartFile dalam, MultipartFile belakang,String desc) throws Exception {
		try {
			lppService.uploadFotoLaporan(id, depan, samping, dalam, belakang,desc);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/progress-lpp/details/done/{id}")
	@Transactional
	public ResponseEntity<?> updateLaporanIsDone(@PathVariable("id") String id) throws Exception {
		try {
			lppService.updateLaporanIsDone(id);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping(value = "admin/details/{id}")
	public ResponseEntity<?> getListLppPersonAdminDetail(@PathVariable("id") String id) throws Exception {
		try {
			return new ResponseEntity<>(lppService.getPersonLppDetailAdmin(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "admin/progress-lpp/details/{id}")
	public ResponseEntity<?> getDetailsLaporanAdmin(@PathVariable("id") String id) throws Exception {
		try {
			return new ResponseEntity<>(lppService.getDetailsLaporanUser(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping(value = "admin/progress-lpp/details/{id}/no-photo")
	public ResponseEntity<?> getDetailsLaporanAdminDtl(@PathVariable("id") String id) throws Exception {
		try {
			return new ResponseEntity<>(lppService.getDetailsLaporanByIdAdminDtl(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
