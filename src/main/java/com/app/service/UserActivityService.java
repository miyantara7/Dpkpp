package com.app.service;

import java.util.List;

import javax.persistence.RollbackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.UserActivityDao;
import com.app.helper.SessionHelper;
import com.app.model.Absent;
import com.app.model.User;
import com.app.model.UserActivity;

@Service
public class UserActivityService {
	
	@Autowired
	private UserActivityDao userActivityDao;
	
	public void valIdExist(UserActivity activityUser) throws Exception{
		if(userActivityDao.getById(activityUser.getId()) == null){
			throw new Exception("UserActivity not exist !");
		}
	}
	
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
	
	public void delete(UserActivity activityUser) throws Exception,RollbackException	 {	
		try {
			valIdExist(activityUser);

			userActivityDao.delete(activityUser);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<UserActivity> getActivitiesByUserId(String userId) throws Exception{
		return userActivityDao.getActivitiesByUserId(userId);
	}
}
