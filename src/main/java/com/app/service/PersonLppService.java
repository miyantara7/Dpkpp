package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.PersonLppDao;
import com.app.helper.SessionHelper;
import com.app.model.Laporan;
import com.app.model.Lpp;
import com.app.model.PersonLpp;

@Service
public class PersonLppService {

	@Autowired
	private PersonLppDao personLppDao;
	
	@Autowired
	private LppService lppService;
	
	@Autowired
	private PersonService personService;
	
	public void add(PersonLpp personLpp) throws Exception{	
		try {
			valIdAndBk(personLpp);
			personLppDao.save(personLpp);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void valIdExist(PersonLpp personLpp) throws Exception{
		if(personLppDao.getById(personLpp.getId()) == null){
			throw new Exception("Laporan not exist !");
		}
	}
	
	public void valBkExist(PersonLpp personLpp) throws Exception{
		lppService.valIdExist(personLpp.getLpp());
		personService.valIdExist(personLpp.getPerson());
	}
	
	public void valIdAndBk(PersonLpp personLpp) throws Exception{
		if(personLppDao.getLppByPersonAndLpp(personLpp.getPerson().getId(), personLpp.getLpp().getId()) != null){
			throw new Exception("Person already assigment !");
		}
	}
	
	public void check(PersonLpp personLpp) throws Exception{
		PersonLpp tempPersonLpp = getById(personLpp.getId());
		if(!tempPersonLpp.getPerson().getId().equals(SessionHelper.getPerson().getId())){
			throw new Exception("Person not match !");
		}
	}
	
	public PersonLpp getById(String id) throws Exception{
		PersonLpp personLpp = personLppDao.getById(id);
		if(personLpp != null) {
			return personLpp;
		}else {
			throw new Exception("PersonLpp not exist !");
		}
	}
	
	public List<PersonLpp> getByBk(String id) throws Exception{
		return personLppDao.getListByBk(id);
	}
	
	public void updatePersonLpp(PersonLpp personLpp) throws Exception{
		valIdExist(personLpp);
		check(personLpp);
		update(personLpp);
	}
	
	public void update(PersonLpp personLpp) throws Exception{	
		try {
			valIdExist(personLpp);
			valBkExist(personLpp);
			
			personLppDao.edit(personLpp);
		} catch (IOException e) {
			throw e;
		}
	}
	
	public void delete(PersonLpp personLpp) throws Exception{	
		try {
			valIdExist(personLpp);
			
			personLppDao.delete(personLpp);
		} catch (IOException e) {
			throw e;
		}
	}
	
	
	public List<PersonLpp> getByPersonId(String id) throws Exception{
		return personLppDao.getByPersonId(id);
	}
}
