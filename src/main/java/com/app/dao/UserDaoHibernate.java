package com.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.Absent;
import com.app.model.Notification;
import com.app.model.User;

@Repository
public class UserDaoHibernate extends BaseDao implements BaseMasterDao {

	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username){
		List<User> listUser = em.createQuery("FROM User where username = :username")
				.setParameter("username", username)
				.getResultList();
		
		return !listUser.isEmpty() ? listUser.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public User getById(String id){
		List<User> listUser = em.createQuery("FROM User where id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !listUser.isEmpty() ? listUser.get(0) : null;
	}
	
	
	@SuppressWarnings("unchecked")
	public User getUserByIdPerson(String id){
		List<User> listUser = em.createQuery("FROM User where person.id = :id")
				.setParameter("id", id)
				.getResultList();
		
		return !listUser.isEmpty() ? listUser.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public boolean valUsernameAndPassword(String username,String password){
		List<User> listUser = em.createQuery("FROM User where username = :username and password = :password")
				.setParameter("username", username)
				.setParameter("password", password)
				.getResultList();
		
		return !listUser.isEmpty() ? true : false;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getByPersonId(String id) {
		
		List<User> list = em.createQuery("From User where person.id=:id")
								.setParameter("id", id)
								.getResultList();
		
		return list;
							
	}
	
	@Override
	public <T> void save(T entity) {
		em.persist(entity);
	}

	@Override
	public <T> void edit(T entity) {
		em.merge(entity);
	}

	@Override
	public <T> void delete(T entity) {
		em.remove(entity);
	}

}
