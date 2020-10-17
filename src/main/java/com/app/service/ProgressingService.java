package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ProgressingDao;
import com.app.model.PersonLpp;
import com.app.model.Progressing;

@Service
public class ProgressingService {

	@Autowired
	private ProgressingDao progressingDao;
	
	public List<Progressing> getAll() throws Exception{
		return progressingDao.getAll();
	}
	

}
