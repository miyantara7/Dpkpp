package com.app.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Position;

@Repository
public class PositionDao extends BaseDao implements BaseMasterDao{

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
	public Position getByCode(String code) throws Exception{
		
		List<Position> list = em.createQuery("FROM Position where code = :code")
				.setParameter("code",code)
				.getResultList();
		
		return !list.isEmpty() ? list.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getAll() throws Exception{	
		List<Object[]> list = em.createNativeQuery(bBuilder("Select id,code,name from tb_position"))
				.getResultList();
		
		List<Object> listUnit = new ArrayList<>();
		
		for(Object[] o : list) {
			LinkedHashMap<String, Object> unit = new LinkedHashMap<>();
			unit.put("key", (String)o[0]);
			unit.put("value", (String)o[1]+" - "+(String)o[2]);
			listUnit.add(unit);
		}
		return !list.isEmpty() ? listUnit : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getAllAbsent() throws Exception{	
		List<Object[]> list = em.createNativeQuery(bBuilder("Select id,name from tb_lov_absent"))
				.getResultList();
		
		List<Object> listAbsent = new ArrayList<>();
		
		for(Object[] o : list) {
			LinkedHashMap<String, Object> absent = new LinkedHashMap<>();
			absent.put("key", (String)o[0]);
			absent.put("value", (String)o[1]);
			listAbsent.add(absent);
		}
		return !list.isEmpty() ? listAbsent : null;
	}

}
