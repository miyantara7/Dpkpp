package com.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Absent;
import com.app.model.UserActivity;
import com.app.pojo.PojoUserActivity;

@Repository
public class UserActivityDao extends BaseDao implements BaseMasterDao {

	@SuppressWarnings("unchecked")
	public List<?> getUserActivity(String id) throws Exception {	
		String sql = bBuilder("select tua.id,tp.name, (tua.date + interval '7 hours')\\:\\:text as date, "+ 
				"tua.type from tb_user_activity tua " + 
				"join tb_users tu on tua.user_id = tu.id " + 
				"join tb_person tp on tu.person_id = tp.id " + 
				"where tu.id = :id");
		List<Object[]> list = em.createNativeQuery(sql)
				.setParameter("id", id)
				.getResultList();
		
		return !list.isEmpty() ? bMapperList(list, PojoUserActivity.class, "id","name","date","type") : null;
	}
	
	@SuppressWarnings("unchecked")
	public UserActivity getById(String id) throws Exception{
		List<UserActivity> results = em.createQuery("FROM UserActivity where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !results.isEmpty() ? results.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserActivity> getActivitiesByUserId(String id) throws Exception{
		List<UserActivity> results = em.createQuery("FROM UserActivity where user.id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return results;
	}
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


}
