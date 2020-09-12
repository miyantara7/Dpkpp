package com.app.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_lov_absent")
public class LovAbsent extends BaseModel {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
public String to_String() {
		if(this.name != null) {
			return  this.name;

		}
		else {
			return "-";
		}
	}
	
}
