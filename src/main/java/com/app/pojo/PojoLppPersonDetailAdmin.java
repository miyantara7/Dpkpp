package com.app.pojo;

import java.util.Date;
import java.util.List;

public class PojoLppPersonDetailAdmin {
	
	private String petugas;
	private Date startDate;
	private Date endDate;
	private Date verikasiDate;
	private String projek;
	private String description;
	private Boolean status;
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProjek() {
		return projek;
	}
	public void setProjek(String projek) {
		this.projek = projek;
	}
	private List<Object> listProggres;
	public String getPetugas() {
		return petugas;
	}
	public void setPetugas(String petugas) {
		this.petugas = petugas;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getVerikasiDate() {
		return verikasiDate;
	}
	public void setVerikasiDate(Date verikasiDate) {
		this.verikasiDate = verikasiDate;
	}
	public List<Object> getListProggres() {
		return listProggres;
	}
	public void setListProggres(List<Object> listProggres) {
		this.listProggres = listProggres;
	}
}
