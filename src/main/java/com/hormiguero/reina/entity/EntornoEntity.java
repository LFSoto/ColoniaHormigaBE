package com.hormiguero.reina.entity;

public class EntornoEntity {

	private String id;
	private int antCost;
	
	public EntornoEntity() {
		setId("");
		setAntCost(0);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setAntCost(int antCost) {
		this.antCost = antCost;
	}
	
	public String getId() {
		return this.id;
	}
	
	public int getAntCost() {
		return this.antCost;
	}
}
