package com.app.dao;

import org.springframework.stereotype.Repository;

import com.app.model.BaseModel;

@Repository
public class LaporanDao extends BaseDao implements BaseMasterDao {

	@Override
	public <T> void save(T entity) throws Exception {
		em.persist(entity);
		
	}

	@Override
	public <T> void edit(T entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void delete(T entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
