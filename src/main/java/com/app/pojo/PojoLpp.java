package com.app.pojo;

import java.util.List;

import com.app.model.Lpp;
import com.app.model.Person;

public class PojoLpp {

	private Lpp lpp;
	private List<Person> listPerson;
	public Lpp getLpp() {
		return lpp;
	}
	public void setLpp(Lpp lpp) {
		this.lpp = lpp;
	}
	public List<Person> getListPerson() {
		return listPerson;
	}
	public void setListPerson(List<Person> listPerson) {
		this.listPerson = listPerson;
	}
}
