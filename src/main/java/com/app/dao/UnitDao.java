package com.app.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Unit;

@Repository
public class UnitDao extends BaseDao implements BaseMasterDao {

	@Override
	public <T> void save(T entity) throws Exception{
		em.persist(entity);
	}

	@Override
	public <T> void edit(T entity) throws Exception{
		em.merge(entity);
	}

	@Override
	public <T> void delete(T entity) throws Exception{
		em.remove(entity);
	}
	
	@SuppressWarnings("unchecked")
	public Unit getByCode(String code) throws Exception{
		
		List<Unit> list = em.createQuery("FROM Unit where code = :code")
				.setParameter("code",code)
				.getResultList();
		
		return !list.isEmpty() ? list.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getAll() throws Exception{	
		List<Object[]> list = em.createNativeQuery(bBuilder("Select id,code,name from tb_unit"))
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
}
