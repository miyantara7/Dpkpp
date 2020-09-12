package com.app.pojo;

public class PojoPerson extends BasePojo {

	private String nip;
	private String name;
	private String gender;
	private String photo;
	private String typeFile;
	private String fileName;
	private String unit;
	private String unitHead;
	private String position;
	
	public PojoPerson() {
		super();
	}
	public PojoPerson(String id,String nip,String name,String gender,String typeFile,String fileName,
			String unit,String unitHead,String position ) {
		this.setId(id);
		this.nip = nip;
		this.name = name;
		this.gender = gender;
		this.typeFile = typeFile;
		this.fileName = fileName;
		this.unit = unit;
		this.unitHead = unitHead;
		this.position = position;
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
	public String getUnitHead() {
		return unitHead;
	}
	public void setUnitHead(String unitHead) {
		this.unitHead = unitHead;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
