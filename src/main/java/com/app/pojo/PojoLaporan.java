package com.app.pojo;


public class PojoLaporan {

	private String id;
	private String name;
	private String uploadDate;
	private String verificationDate;
	private String dec;
	public Object fotoDepan;
	public Object fotoSamping;
	public Object fotoBelakang;
	public Object fotoDalam;
	private String projek;
	private String proggress;
	
	
	
	public String getProjek() {
		return projek;
	}
	public void setProjek(String projek) {
		this.projek = projek;
	}
	public String getProggress() {
		return proggress;
	}
	public void setProggress(String proggress) {
		this.proggress = proggress;
	}
	public Object getFotoDepan() {
		return fotoDepan;
	}
	public void setFotoDepan(Object fotoDepan) {
		this.fotoDepan = fotoDepan;
	}
	public Object getFotoSamping() {
		return fotoSamping;
	}
	public void setFotoSamping(Object fotoSamping) {
		this.fotoSamping = fotoSamping;
	}
	public Object getFotoBelakang() {
		return fotoBelakang;
	}
	public void setFotoBelakang(Object fotoBelakang) {
		this.fotoBelakang = fotoBelakang;
	}
	public Object getFotoDalam() {
		return fotoDalam;
	}
	public void setFotoDalam(Object fotoDalam) {
		this.fotoDalam = fotoDalam;
	}
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
}
