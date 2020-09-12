package com.app.helper;

import org.springframework.security.core.context.SecurityContextHolder;

import com.app.model.Person;
import com.app.model.User;
import com.app.model.Users;

public class SessionHelper {
	
	public static String getUserId() {
		return ((Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
	}
	
	public static Person getPerson() {
		return ((Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getPerson();
	}
	
	public static User getUser() {
		return ((Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	}
}
