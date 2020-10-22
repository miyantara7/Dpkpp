package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_notification")
public class Notification extends BaseModel {
	
	private String code;
	private String title;
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	@Column(name = "isRead")
	private Boolean isRead;
	@ManyToOne
	@JoinColumn(name = "person_lpp_id")
	private PersonLpp personLpp;
	
	
	public PersonLpp getPersonLpp() {
		return personLpp;
	}
	public void setPersonLpp(PersonLpp personLpp) {
		this.personLpp = personLpp;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public Laporan getLaporan() {
		return laporan;
	}
	public void setLaporan(Laporan laporan) {
		this.laporan = laporan;
	}
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "laporan_id")
	private Laporan laporan;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}
