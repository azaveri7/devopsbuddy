package com.devopsbuddy.backend.persistence.domain.backend;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.devopsbuddy.enums.PlansEnum;

@Entity
public class Plan implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Plan() {
		
	}
	
	public Plan(PlansEnum plansEnum) {
		this.id = plansEnum.getId();
		this.name = plansEnum.getPlanName();
	}
	
}
