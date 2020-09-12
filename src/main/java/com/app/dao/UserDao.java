package com.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {
	
	User findByUsername(String username);
}