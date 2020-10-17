package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_laporan")
public class Laporan extends BaseModel{
	
	@ManyToOne
	@JoinColumn(name = "person_lpp_id")
	private PersonLpp personLpp;
	@ManyToOne
	@JoinColumn(name = "progress_id")
	private Progressing progressing;
	
	@Column(columnDefinition="TEXT")
	private String typeFileDepan;
	@Column(columnDefinition="TEXT")
	private String fileNameDepan;
	
	@Column(columnDefinition="TEXT")
	private String typeFileSamping;
	@Column(columnDefinition="TEXT")
	private String fileNameSamping;
	
	@Column(columnDefinition="TEXT")
	private String typeFileDalam;
	@Column(columnDefinition="TEXT")
	private String fileNameDalam;
	
	@Column(columnDefinition="TEXT")
	private String typeFileBelakang;
	@Column(columnDefinition="TEXT")
	private String fileNameBelakang;
	@Column(name = "description")
	private String desc;
	private Boolean status = false;
	public PersonLpp getPersonLpp() {
		return personLpp;
	}

	public void setPersonLpp(PersonLpp personLpp) {
		this.personLpp = personLpp;
	}

	public Progressing getProgressing() {
		return progressing;
	}

	public void setProgressing(Progressing progressing) {
		this.progressing = progressing;
	}

	public String getTypeFileDepan() {
		return typeFileDepan;
	}

	public void setTypeFileDepan(String typeFileDepan) {
		this.typeFileDepan = typeFileDepan;
	}

	public String getFileNameDepan() {
		return fileNameDepan;
	}

	public void setFileNameDepan(String fileNameDepan) {
		this.fileNameDepan = fileNameDepan;
	}

	public String getTypeFileSamping() {
		return typeFileSamping;
	}

	public void setTypeFileSamping(String typeFileSamping) {
		this.typeFileSamping = typeFileSamping;
	}

	public String getFileNameSamping() {
		return fileNameSamping;
	}

	public void setFileNameSamping(String fileNameSamping) {
		this.fileNameSamping = fileNameSamping;
	}

	public String getTypeFileDalam() {
		return typeFileDalam;
	}

	public void setTypeFileDalam(String typeFileDalam) {
		this.typeFileDalam = typeFileDalam;
	}

	public String getFileNameDalam() {
		return fileNameDalam;
	}

	public void setFileNameDalam(String fileNameDalam) {
		this.fileNameDalam = fileNameDalam;
	}

	public String getTypeFileBelakang() {
		return typeFileBelakang;
	}

	public void setTypeFileBelakang(String typeFileBelakang) {
		this.typeFileBelakang = typeFileBelakang;
	}

	public String getFileNameBelakang() {
		return fileNameBelakang;
	}

	public void setFileNameBelakang(String fileNameBelakang) {
		this.fileNameBelakang = fileNameBelakang;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
