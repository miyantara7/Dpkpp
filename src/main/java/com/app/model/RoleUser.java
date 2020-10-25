package com.app.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_role_user")
public class RoleUser extends BaseModel {

	private String name;
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
