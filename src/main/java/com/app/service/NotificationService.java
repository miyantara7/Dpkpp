package com.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.NotificationDao;
import com.app.helper.SessionHelper;
import com.app.model.Notification;
import com.app.model.Person;

@Service
@Transactional
public class NotificationService {
	
	@Autowired
	private NotificationDao notDao;
	
	public void save(Notification not) throws Exception {
		try {
			notDao.save(not);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			// TODO: handle exception
		}
	}
	public List<Object> getNotif(){
		 return this.notDao.getNotifVericator(SessionHelper.getUser().getRoleUser().getName());
	}
	
	public void valIdExist(Notification not) throws Exception{
		if(notDao.getById(not.getId()) == null){
			throw new Exception("Notif not exist !");
		}
	}
	
	public void updateNotifisRead(String id) throws Exception {
		Notification ns = notDao.getById(id);
		if(ns == null) {
			throw new Exception("Notif not Found");
		}
		ns.setIsRead(true);
		notDao.edit(ns);
	}
	
	public void delete(Notification not) throws Exception {
		try {
			valIdExist(not);
			notDao.delete(not);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Notification> getByPersonId(String id) throws Exception{
		return notDao.getByPersonId(id);
	}
}
