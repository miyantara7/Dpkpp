package com.app.service;

import java.io.IOException;

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
	
	public void add(PersonLpp personLpp) throws Exception{	
		try {
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
		PersonLpp tempPersonLpp = getById(personLpp.getId());
		if(!personLpp.getPerson().getId().equals(SessionHelper.getPerson().getId())){
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
	
	public void update(PersonLpp personLpp) throws Exception{	
		try {
			valIdExist(personLpp);
			valBkExist(personLpp);
			
			personLppDao.edit(personLpp);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
