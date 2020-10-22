package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_person",uniqueConstraints = 
{@UniqueConstraint(name = "u_nip", columnNames = {"nip"})})
public class Person extends BaseModel{

	@Column(columnDefinition="TEXT")
	private String name;
	@Column(columnDefinition="TEXT")
	private String nip;
	@Column(columnDefinition="TEXT")
	private String gender;
	@Transient
	private String photo;
	@Column(columnDefinition="TEXT")
	private String typeFile;
	@Column(columnDefinition="TEXT")
	private String fileName;

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getTypeFile() {
		return typeFile;
	}
	public void setTypeFile(String typeFile) {
		this.typeFile = typeFile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

}
