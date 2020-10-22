package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.LovAbsentDao;
import com.app.model.LovAbsent;

@Service
public class LovAbsentService {
	
	@Autowired
	private LovAbsentDao lovDao;
	
	
	public List<LovAbsent> get(){
		return lovDao.getLov();
	}

}
