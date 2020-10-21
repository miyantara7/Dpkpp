package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.RoleUserDao;
import com.app.model.RoleUser;

@Service
public class RoleUserService {

	@Autowired
	private RoleUserDao roleUserDao;
	
	public RoleUser getRole(String role) throws Exception{
		return roleUserDao.getRole(role);
	}
	
	public List<RoleUser> getRoleList() throws Exception{
		return roleUserDao.getRoleList();
	}
}
