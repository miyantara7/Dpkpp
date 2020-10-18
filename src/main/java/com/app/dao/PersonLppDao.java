package com.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Lpp;
import com.app.model.PersonLpp;

@Repository
public class PersonLppDao extends BaseDao implements BaseMasterDao {

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
	    em.remove(entity);
		
	}
	
	@SuppressWarnings("unchecked")
	public PersonLpp getById(String id) throws Exception{
		List<PersonLpp> results = em.createQuery("FROM PersonLpp where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !results.isEmpty() ? results.get(0) : null;
	}

}
