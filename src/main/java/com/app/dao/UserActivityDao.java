package com.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.pojo.PojoUserActivity;

@Repository
public class UserActivityDao extends BaseDao implements BaseMasterDao {

	@SuppressWarnings("unchecked")
	public List<?> getUserActivity(String id) throws Exception {	
		String sql = bBuilder("select tua.id,tp.name, tua.date\\:\\:text, "+ 
				"tua.type from tb_user_activity tua " + 
				"join tb_users tu on tua.user_id = tu.id " + 
				"join tb_person tp on tu.person_id = tp.id " + 
				"where tu.id = :id");
		List<Object[]> list = em.createNativeQuery(sql)
				.setParameter("id", id)
				.getResultList();
		
		return !list.isEmpty() ? bMapperList(list, PojoUserActivity.class, "id","name","date","type") : null;
	}
	
	@Override
	public <T> void save(T entity) throws Exception{
		em.persist(entity);	
	}

	@Override
	public <T> void edit(T entity) throws Exception{
		
	}

	@Override
	public <T> void delete(T entity) throws Exception{

	}


}
