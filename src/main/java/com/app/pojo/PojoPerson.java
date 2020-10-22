package com.app.pojo;

public class PojoPerson extends BasePojo {

	private String nip;
	private String name;
	private String gender;
	private String photo;
	private String typeFile;
	private String fileName;
	
	public PojoPerson() {
		super();
	}
	public PojoPerson(String id,String nip,String name,String gender,String typeFile,String fileName ) {
		this.setId(id);
		this.nip = nip;
		this.name = name;
		this.gender = gender;
		this.typeFile = typeFile;
		this.fileName = fileName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
