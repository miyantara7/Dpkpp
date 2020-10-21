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

	@SuppressWarnings("unchecked")
	public List<PersonLpp> getListByBk(String id) throws Exception{
		List<PersonLpp> results = em.createQuery("FROM PersonLpp where lpp.id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public PersonLpp getLppByPersonAndLpp(String personId,String lppId) throws Exception{
		List<PersonLpp> results = em.createQuery("FROM PersonLpp where person.id = :personId and lpp.id = :lppId")
				.setParameter("personId", personId)
				.setParameter("lppId", lppId)
				.getResultList();
		
		return !results.isEmpty() ? results.get(0) : null;
	}
}
