package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.UnitPositionDao;
import com.app.model.UnitPosition;
import com.app.pojo.PojoUnitPositionSelector;

@Service
public class UnitPositionService {

	@Autowired
	private UnitPositionDao unitPositionDao;
	
	public void valIdNull(UnitPosition unitPosition) throws Exception{
		if (unitPosition.getId() != null) {
			throw new Exception("Id must be empty !");
		}
	
	}
	
	public void valIdNotNull(UnitPosition unitPosition) throws Exception{
		if (unitPosition.getUnit().getId().isEmpty() || unitPosition.getUnit().getId() == null) {
			throw new Exception("Unit id cannot be empty !");
		}
		
		if (unitPosition.getPosition().getId().isEmpty() || unitPosition.getPosition().getId() == null) {
			throw new Exception("Position id cannot be empty !");
		}
	}
	
	
	public void add(UnitPosition unitPosition) throws Exception{
		try {
			valIdNull(unitPosition);
			valIdNotNull(unitPosition);
			unitPositionDao.save(unitPosition);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Object> getUnitPositionByBk(PojoUnitPositionSelector unitPosition) throws Exception {
		return unitPositionDao.getUnitPositionByBk(unitPosition);
	}
}
