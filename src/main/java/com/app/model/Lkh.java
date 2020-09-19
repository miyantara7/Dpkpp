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
@Table(name = "tb_lkh")
public class Lkh extends BaseModel {

	@Temporal(TemporalType.DATE)
	private Date endDate;
	@Temporal(TemporalType.DATE)
	private Date valDate;
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	
	@Column(name = "description")
	private String desc;
	
	private boolean status;

	@Column(columnDefinition="TEXT")
	private String typeFile;
	@Column(columnDefinition="TEXT")
	private String fileName;
	@Column(columnDefinition="TEXT")
	private String hasil;
	@Column(columnDefinition="TEXT")
	private String laporan;
	public String getHasil() {
		return hasil;
	}
	public void setHasil(String hasil) {
		this.hasil = hasil;
	}
	public String getLaporan() {
		return laporan;
	}
	public void setLaporan(String laporan) {
		this.laporan = laporan;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getValDate() {
		return valDate;
	}
	public void setValDate(Date valDate) {
		this.valDate = valDate;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
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
}
