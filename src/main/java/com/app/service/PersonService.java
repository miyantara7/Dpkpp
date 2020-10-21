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
import com.app.model.Laporan;
import com.app.model.Lpp;
import com.app.model.Person;
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

	public void save(Person person) throws Exception {
		try {
			if (personDao.getPersonByNip(person.getNip()) != null) {
				throw new Exception("Person is exist !");
			} else {
				personDao.save(person);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void editPerson(MultipartFile file) throws Exception {
		File fileDel = null;
		try {
			Person tempPerson = personDao.getPersonById(SessionHelper.getPerson().getId());
			if(tempPerson!=null) {
				if (file != null) {
					fileDel = new File(path + "/" + tempPerson.getId() + "_" + tempPerson.getFileName());
					fileDel.delete();
					System.out.println("masuk file");
					tempPerson.setTypeFile(file.getContentType());
					tempPerson.setFileName(file.getOriginalFilename());
				}
				System.out.println(tempPerson.getName());
				if (file != null) {
					InputStream is = file.getInputStream();
					Files.copy(is, Paths.get(path + tempPerson.getId() + "_" + tempPerson.getFileName()),
							StandardCopyOption.REPLACE_EXISTING);
				} 
				
				edit(tempPerson);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void edit(Person person) throws Exception {
		try {
			personDao.edit(person);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void editPerson(Person person) throws Exception {
		try {
			Person ps = personDao.getById(SessionHelper.getPerson().getId());
			System.out.println("ini geneder"+person.getGender());
			ps.setName(person.getName());
			ps.setNip(person.getNip());
			ps.setGender(person.getGender());
			personDao.edit(ps);
		} catch (Exception e) {
			throw e;
		}
	}

	public Person findbyId() throws Exception {
		Person person = personDao.getPersonById(SessionHelper.getPerson().getId());
		String filePath = person.getId() + "_" + person.getFileName();
		String photo;
		File file;
		file = new File(path + filePath);
		if (file.exists()) {
			try {
				photo = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
				person.setPhoto(photo);
				person.setFileName(person.getFileName());
				person.setTypeFile(person.getTypeFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				file = new File(path + photoNotFound);
				photo = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
				person.setPhoto(photo);
				person.setFileName(file.getName());
				person.setTypeFile("image/"+FilenameUtils.getExtension(file.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return person;
	}

	public HashMap<String, Object> findbyIdAdmin(String id) throws Exception {

		HashMap<String, Object> rtData = new HashMap<String, Object>();
		Person person = personDao.getPersonById(id);
		String filePath = person.getId() + "_" + person.getFileName();
		String photo;
		File file;
		file = new File(path + filePath);
		if (file.exists()) {
			try {
				photo = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
				person.setPhoto(photo);
				person.setFileName(person.getFileName());
				person.setTypeFile(person.getTypeFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				file = new File(path + photoNotFound);
				photo = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
				person.setPhoto(photo);
				person.setFileName(file.getName());
				person.setTypeFile("image/"+FilenameUtils.getExtension(file.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		rtData.put("person", person);
		rtData.put("absen", absentService.getUserAbsentByIdAdmin(id));
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

	public List<Object[]> getPojoPersonById(String id) throws Exception {
		return personDao.getPojoPersonById(id);
	}
	
	public List<Object> getPetugas() throws Exception {
		return personDao.getPetugas();
	}
}
