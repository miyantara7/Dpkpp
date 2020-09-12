package com.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_absent")
public class Absent extends BaseModel {

	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateIn;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOut;
	@Column(columnDefinition="TEXT")
	private String status;
	@ManyToOne
	@JoinColumn(name = "lov_absent_id")
	private LovAbsent lovAbsent;

	@Column(columnDefinition="TEXT")
	private String typeFileAbsenIn;
	@Column(columnDefinition="TEXT")
	private String fileNameAbsenIn;
	@Column(columnDefinition="TEXT")
	private String typeFileAbsenOut;
	@Column(columnDefinition="TEXT")
	private String fileNameAbsenOut;
	@Column(columnDefinition="TEXT")
	private String longtitudeAbsenIn;
	@Column(columnDefinition="TEXT")
	private String langtitudeAbsenIn;
	@Column(columnDefinition="TEXT")
	private String longtitudeAbsenOut;
	@Column(columnDefinition="TEXT")
	private String langtitudeAbsenOut;
	@Column(columnDefinition="TEXT")
	private String locationAbsenIn;
	@Column(columnDefinition="TEXT")
	private String locationAbsenOut;
	@Column(columnDefinition="TEXT")
	private String createdBy;
	@Column(columnDefinition="TEXT")
	private String updatedBy;
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLocationAbsenIn() {
		return locationAbsenIn;
	}
	public void setLocationAbsenIn(String locationAbsenIn) {
		this.locationAbsenIn = locationAbsenIn;
	}
	public String getLocationAbsenOut() {
		return locationAbsenOut;
	}
	public void setLocationAbsenOut(String locationAbsenOut) {
		this.locationAbsenOut = locationAbsenOut;
	}
	public String getTypeFileAbsenIn() {
		return typeFileAbsenIn;
	}
	public void setTypeFileAbsenIn(String typeFileAbsenIn) {
		this.typeFileAbsenIn = typeFileAbsenIn;
	}
	public String getFileNameAbsenIn() {
		return fileNameAbsenIn;
	}
	public void setFileNameAbsenIn(String fileNameAbsenIn) {
		this.fileNameAbsenIn = fileNameAbsenIn;
	}
	public String getTypeFileAbsenOut() {
		return typeFileAbsenOut;
	}
	public void setTypeFileAbsenOut(String typeFileAbsenOut) {
		this.typeFileAbsenOut = typeFileAbsenOut;
	}
	public String getFileNameAbsenOut() {
		return fileNameAbsenOut;
	}
	public void setFileNameAbsenOut(String fileNameAbsenOut) {
		this.fileNameAbsenOut = fileNameAbsenOut;
	}
	public String getLongtitudeAbsenIn() {
		return longtitudeAbsenIn;
	}
	public void setLongtitudeAbsenIn(String longtitudeAbsenIn) {
		this.longtitudeAbsenIn = longtitudeAbsenIn;
	}
	public String getLangtitudeAbsenIn() {
		return langtitudeAbsenIn;
	}
	public void setLangtitudeAbsenIn(String langtitudeAbsenIn) {
		this.langtitudeAbsenIn = langtitudeAbsenIn;
	}
	public String getLongtitudeAbsenOut() {
		return longtitudeAbsenOut;
	}
	public void setLongtitudeAbsenOut(String longtitudeAbsenOut) {
		this.longtitudeAbsenOut = longtitudeAbsenOut;
	}
	public String getLangtitudeAbsenOut() {
		return langtitudeAbsenOut;
	}
	public void setLangtitudeAbsenOut(String langtitudeAbsenOut) {
		this.langtitudeAbsenOut = langtitudeAbsenOut;
	}
	public LovAbsent getLovAbsent() {
		return lovAbsent;
	}
	public void setLovAbsent(LovAbsent lovAbsent) {
		this.lovAbsent = lovAbsent;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
