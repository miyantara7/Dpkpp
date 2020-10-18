package com.app.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_lpp")
public class Lpp extends BaseModel {

	@Column(columnDefinition="TEXT")
	private String code;
	@Column(columnDefinition="TEXT")
	private String name;
	@Column(name = "description",columnDefinition="TEXT")
	private String desc;
	@Column(columnDefinition="TEXT")
	private String location;
	@Column(columnDefinition="TEXT")
	private String longtitude;
	@Column(columnDefinition="TEXT")
	private String langtitude;
	@Column(columnDefinition="TEXT")
	private String typeFile;
	@Column(columnDefinition="TEXT")
	private String fileName;
	private String createdBy;
	private Timestamp createdAt;
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	@Transient
	@JsonIgnore
	private Date startDate;
	public String getTypeFile() {
		return typeFile;
	}
	public void setTypeFile(String typeFile) {
		this.typeFile = typeFile;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}
	public String getLangtitude() {
		return langtitude;
	}
	public void setLangtitude(String langtitude) {
		this.langtitude = langtitude;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
