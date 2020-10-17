package com.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Progressing;

@Repository
public class ProgressingDao extends BaseDao implements BaseMasterDao{
	
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

	@SuppressWarnings("unchecked")
	public List<Progressing> getAll() throws Exception{
		List<Progressing> list = em.createQuery(bBuilder("FROM Progressing"))
				.getResultList();
		
		return list;
	}



}
