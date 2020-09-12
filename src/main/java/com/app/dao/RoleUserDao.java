package com.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.model.RoleUser;

@Repository
public class RoleUserDao extends BaseDao {

	@SuppressWarnings("unchecked")
	public RoleUser getRole(String role) {
		List<RoleUser> list = em.createQuery("From RoleUser where name = :role")
				.setParameter("role", role)
				.getResultList();
		
		return !list.isEmpty() ? list.get(0) : null;
	}
}
