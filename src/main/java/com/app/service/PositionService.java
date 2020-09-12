package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.PositionDao;
import com.app.model.Position;

@Service
public class PositionService {

	@Autowired
	private PositionDao positionDao;
	
	public void valIdNull(Position position) throws Exception{
		if ( position.getId() != null) {
			throw new Exception("Id must be empty !");
		}
	}
	
	public void valNonBk(Position position) throws Exception{
		
		if (position.getName().isEmpty()) {
			throw new Exception("Name cannot be empty !");
		}
		
		if (position.getCode().isEmpty()) {
			throw new Exception("Code cannot be empty !");
		}
		
		if (String.valueOf(position.getLevel()).isEmpty()) {
			throw new Exception("Level cannot be empty !");
		}
		
		if (positionDao.getByCode(position.getCode()) != null) {
			throw new Exception("Position is exist !");
		}
	}
	
	public void add(Position position) throws Exception{
		try {
			valIdNull(position);
			valNonBk(position);
			positionDao.save(position);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Object> getAllPosition() throws Exception{
		return positionDao.getAll();
	}
	
	public List<Object> getAllAbsent() throws Exception{
		return positionDao.getAllAbsent();
	}
}
