package com.app.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.PersonLppDao;
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
}
