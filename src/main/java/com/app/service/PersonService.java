package com.app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.PersonDao;
import com.app.helper.SessionHelper;
import com.app.model.Absent;
import com.app.model.Laporan;
import com.app.model.Lpp;
import com.app.model.Notification;
import com.app.model.Person;
import com.app.model.PersonLpp;
import com.app.model.User;
import com.app.model.UserActivity;
import com.app.pojo.PojoPagination;

@Service
@Transactional
public class PersonService extends BaseService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private AbsentSevice absentService;

	@Value("${path.photo}")
    private String path;

	@Value("${photo.not.found}")
	private String photoNotFound;
	
	@Value("${upload.path}")
    private String path_default;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private NotificationService notService;
	
	@Autowired
	private PersonLppService personLppService;
	
	@Autowired
	private LppService lppService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	public Person getById(String id) throws Exception{
		Person person = personDao.getById(id);
		if(person != null) {
			return person;
		}else {
			throw new Exception("Person not exist !");
		}
	}
	
	public void valIdExist(Person person) throws Exception{
		if(personDao.getById(person.getId()) == null){
			throw new Exception("Person not exist !");
		}
	}
	
	public void valIdNotNull(Person person) throws Exception{
		if(person.getId().equals("") || person.getId() == null){
			throw new Exception("Person id cannot be null !");
		}
	}
	
	public void valIdNull(Person person) throws Exception{
		if(!person.getId().equals("") || person.getId() != null){
			throw new Exception("Person id must be null !");
		}
	}
	
	public void valIdBkExist(Person person) throws Exception{
		if (personDao.getPersonByNip(person.getNip()) != null) {
			throw new Exception("Person is exist !");
		}
	}

	public void save(Person person) throws Exception {
		try {
			valIdBkExist(person);
			valIdNull(person);
			personDao.save(person);
		} catch (Exception e) {
			throw e;
		}
	}
	public void editPerson(MultipartFile file) throws Exception {
		try {
			Person tempPerson = getById(SessionHelper.getPerson().getId());
			if(tempPerson!=null) {
				if (file != null) {
					fileService.editFotoPerson(tempPerson, file);
					tempPerson.setTypeFile(file.getContentType());
					tempPerson.setFileName(file.getOriginalFilename());
				}

				edit(tempPerson);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void edit(Person person) throws Exception {
		try {
			valIdNotNull(person);
			personDao.edit(person);
		} catch (Exception e) {
			throw e;
		}
	}
	public void delete(Person person) throws Exception {
		try {
			valIdExist(person);
			personDao.delete(person);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void editPerson(Person person) throws Exception {
		try {
			Person ps = personDao.getById(SessionHelper.getPerson().getId());
			ps.setName(person.getName());
			ps.setNip(person.getNip());
			ps.setGender(person.getGender());
			personDao.edit(ps);
		} catch (Exception e) {
			throw e;
		}
	}

	public Person findbyId() throws Exception {
		try {
			Person person = personDao.getById(SessionHelper.getPerson().getId());
			fileService.getFotoPerson(person);
			return person;
		} catch (Exception e) {
			throw e;
		}
	}

	public HashMap<String, Object> findbyIdAdmin(String id) throws Exception {

		HashMap<String, Object> rtData = new HashMap<String, Object>();
		Person person = personDao.getById(id);
		User user = userService.getUserbyIdPersonAdmin(id);
		rtData.put("person", fileService.getFotoPerson(person));
		rtData.put("absen", absentService.getUserAbsentByIdAdmin(id));
		rtData.put("user",user);
		return rtData;
	}

	public PojoPagination getAllPersonByPaging(int page, int limit) throws Exception {
		PojoPagination pojo = new PojoPagination();
		pojo.setData(personDao.getAllPersonByPaging(page, limit,null));
		pojo.setCount(personDao.getCountPersonByPaging(null));
		return pojo;
	}

	public PojoPagination getAllPersonBySearch(int page, int limit, String inquiry) throws Exception {
		PojoPagination pojo = new PojoPagination();
		pojo.setData(personDao.getAllPersonByPaging(page, limit, inquiry));
		pojo.setCount(personDao.getCountPersonByPaging(inquiry));
		return pojo;
	}

	public List<Object> getPetugas() throws Exception {
		return personDao.getPetugas();
	}
	@Transactional
	public void deletePersonDetail(Person person) throws Exception{
		try {
			String id = person.getId();
			List<Notification> listnotification = notService.getByPersonId(id);
			if (!listnotification.isEmpty()) {
				for (Notification not : listnotification) {
					notService.delete(not);
				}
			}
			
			List<PersonLpp> listPersonLpp = personLppService.getByPersonId(id);
			if (!listPersonLpp.isEmpty()) {
				for (PersonLpp personLpp : listPersonLpp) {
					lppService.deleteLaporan(personLpp.getId());
					personLppService.delete(personLpp);
				}
			}
			
			List<Lpp> listLpp = lppService.getByPersonId(id);
			if (!listLpp.isEmpty()) {
				for (Lpp lpp : listLpp) {
					lppService.delete(lpp);
				}
			}
			
			List<Absent> listAbsent = absentService.getByPersonId(id);
			if (!listAbsent.isEmpty()) {
				for (Absent absent : listAbsent) {
					absentService.delete(absent);
				}
			}
			
			List<User> listUser = userService.getByPersonId(id);
			if (!listUser.isEmpty()) {
				for (User user : listUser) {
					List<UserActivity> listActivity = userActivityService.getActivitiesByUserId(user.getId());
					if (!listActivity.isEmpty()) {
						for (UserActivity activityUser : listActivity) {
							userActivityService.delete(activityUser);
						}
					}
					userService.delete(user);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	@Transactional
	public void deletePerson(List<Person> listPerson) throws Exception{
		for(Person person : listPerson) {
			deletePersonDetail(person);
			delete(getById(person.getId()));
		}
	}
}
