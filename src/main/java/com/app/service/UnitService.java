package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.UnitDao;
import com.app.model.Unit;

@Service
public class UnitService {

	@Autowired
	private UnitDao unitDao;
	
	public void valIdNull(Unit unit) throws Exception{
		if (unit.getId() != null) {
			throw new Exception("Id must be empty !");
		}
	}
	
	public void valNonBk(Unit unit) throws Exception{
		
		if (unit.getName().isEmpty()) {
			throw new Exception("Name cannot be empty !");
		}
		
		if (unit.getCode().isEmpty()) {
			throw new Exception("Code cannot be empty !");
		}
		
		if (unitDao.getByCode(unit.getCode()) != null) {
			throw new Exception("Unit is exist !");
		}
	}
	
	public void add(Unit unit) throws Exception{
		try {
			valIdNull(unit);
			valNonBk(unit);
			
			unitDao.save(unit);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Object> getAll() throws Exception{
		return unitDao.getAll();
	}
	
	
}
