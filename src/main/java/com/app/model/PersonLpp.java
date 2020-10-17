package com.app.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_person_lpp")
public class PersonLpp extends BaseModel {
	
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	
	@ManyToOne
	@JoinColumn(name = "lpp_id")
	private Lpp lpp;
	
	private Boolean status=false;
	
	private Timestamp createdAt;
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Lpp getLpp() {
		return lpp;
	}

	public void setLpp(Lpp lpp) {
		this.lpp = lpp;
	}

	public Boolean getStatus() {
		return status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
