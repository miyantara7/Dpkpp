package com.app.pojo;

import com.app.model.RoleUser;

public class PojoUser extends BasePojo {

	private String username;
	private PojoPerson person;
	private RoleUser roleUser;
	public PojoUser() {
		super();
	}
	public PojoUser(String id,String username,PojoPerson person,RoleUser roleUser ) {
		this.setId(id);
		this.username = username;
		this.person = person;
		this.roleUser = roleUser;
	}
	public RoleUser getRoleUser() {
		return roleUser;
	}
	public void setRoleUser(RoleUser roleUser) {
		this.roleUser = roleUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public PojoPerson getPerson() {
		return person;
	}
	public void setPerson(PojoPerson person) {
		this.person = person;
	}
	
}
