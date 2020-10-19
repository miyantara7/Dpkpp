package com.app.pojo;

public class PojoLppPetugas {
	private String id;
	private String laporanId;
	private String nameLpp;
	private String petugas;
	private String desc;
	private String startDate;
	private String endDate;
	private Boolean status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLaporanId() {
		return laporanId;
	}
	public void setLaporanId(String laporanId) {
		this.laporanId = laporanId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getNameLpp() {
		return nameLpp;
	}
	public void setNameLpp(String nameLpp) {
		this.nameLpp = nameLpp;
	}
	public String getPetugas() {
		return petugas;
	}
	public void setPetugas(String petugas) {
		this.petugas = petugas;
	}
}
