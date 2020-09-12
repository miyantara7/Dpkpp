package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.UserActivityDao;
import com.app.helper.SessionHelper;
import com.app.model.UserActivity;

@Service
public class UserActivityService {
	
	@Autowired
	private UserActivityDao userActivityDao;
	
	public void save(UserActivity user) throws Exception{
		try {
			userActivityDao.save(user);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public List<?> getUserActivity() throws Exception{	
		try {
			return userActivityDao.getUserActivity(SessionHelper.getUserId());
		} catch (Exception e) {
			throw e;
		}
		
	}
}
