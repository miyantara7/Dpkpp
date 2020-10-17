package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.app.dao.ProgressingDao;
import com.app.dao.SqlDao;
import com.app.model.Progressing;

@Service
public class SqlService {

	@Autowired
	private ProgressingDao progDao;
	
	@Autowired
	private SqlDao sqlDao;
	
	public void insert() throws Exception{
		try {
			List<Progressing> list = progDao.getAll();
			if(list.isEmpty()) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void generateUUID() throws Exception{
		try {
			sqlDao.generateUUID();
		} catch (Exception e) {
			throw e;
		}
	}
}
