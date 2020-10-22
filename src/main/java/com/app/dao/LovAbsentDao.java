package com.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.LovAbsent;

@Repository
public class LovAbsentDao extends BaseDao implements BaseMasterDao {

	@Override
	public <T> void save(T entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void edit(T entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void delete(T entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public List<LovAbsent> getLov(){
		 List<LovAbsent> get = em.createQuery("FROM LovAbsent").getResultList();
	return get;
	}
	

}
