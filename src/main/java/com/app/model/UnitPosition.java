package com.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_unit_position")
public class UnitPosition extends BaseModel {
	
	@ManyToOne
	@JoinColumn(name = "unit_id" ,nullable = true,columnDefinition="TEXT")
	private Unit unit;
	@ManyToOne
	@JoinColumn(name = "position_id" ,nullable = true,columnDefinition="TEXT")
	private Position position;
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
}
