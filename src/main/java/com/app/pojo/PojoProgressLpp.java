package com.app.pojo;

import java.util.LinkedHashMap;
import java.util.List;

public class PojoProgressLpp {

	private String id;
	private String name;
	private String startDate;
	private String endDate;
	private String verificationDate;
	private List<Object> listProgress;
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
	public String getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}
	public List<Object> getListProgress() {
		return listProgress;
	}
	public void setListProgress(List<Object> listProgress) {
		this.listProgress = listProgress;
	}
}
