package com.app.pojo;

import java.util.List;

public class PojoLaporan {

	private String id;
	private String name;
	private String uploadDate;
	private String verificationDate;
	private String dec;
	private List<Object> listFoto;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}
	public String getDec() {
		return dec;
	}
	public void setDec(String dec) {
		this.dec = dec;
	}
	public List<Object> getListFoto() {
		return listFoto;
	}
	public void setListFoto(List<Object> listFoto) {
		this.listFoto = listFoto;
	}
}
