package com.app.pojo;

import java.util.List;

public class PojoPagination {

	private List<?> data;
	private Integer count;
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
