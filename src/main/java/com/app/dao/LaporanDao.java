package com.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.BaseModel;
import com.app.model.Laporan;
import com.app.model.Lpp;

@Repository
public class LaporanDao extends BaseDao implements BaseMasterDao {

	@Override
	public <T> void save(T entity) throws Exception {
		em.persist(entity);
		
	}

	@Override
	public <T> void edit(T entity) throws Exception {
		em.merge(entity);
	}

	@Override
	public <T> void delete(T entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public Laporan getById(String id) throws Exception{
		List<Laporan> results = em.createQuery("FROM Laporan where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !results.isEmpty() ? results.get(0) : null;
	}

}
