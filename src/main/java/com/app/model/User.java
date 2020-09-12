package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_users",uniqueConstraints = {@UniqueConstraint(name = "u_username", columnNames = {"username"})})
public class User extends BaseModel {

	@Column(columnDefinition="TEXT")
	private String username;
	
	@Column(columnDefinition="TEXT")
	@JsonIgnore
	private String password;
	
	@OneToOne
	@JoinColumn(name = "person_id" ,nullable = false)
	private Person person;
	
	@Column(nullable = true,columnDefinition = "boolean default false")
	@JsonIgnore
	private boolean isActiveMobile ;
	
	@Column(nullable = true,columnDefinition = "boolean default false")
	@JsonIgnore
	private boolean isActiveWeb ;
	
	@OneToOne
	@JoinColumn(name = "role_user_id")
	private RoleUser roleUser;

	public RoleUser getRoleUser() {
		return roleUser;
	}

	public void setRoleUser(RoleUser roleUser) {
		this.roleUser = roleUser;
	}

	public boolean isActiveMobile() {
		return isActiveMobile;
	}

	public void setActiveMobile(boolean isActiveMobile) {
		this.isActiveMobile = isActiveMobile;
	}

	public boolean isActiveWeb() {
		return isActiveWeb;
	}

	public void setActiveWeb(boolean isActiveWeb) {
		this.isActiveWeb = isActiveWeb;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}