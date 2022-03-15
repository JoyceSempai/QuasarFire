package com.cruiser.quasarfire.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Satellite {

	private @Id String name;
	private double distance;
	private String[] message;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public String[] getMessage() {
		return message;
	}
	
	public void setMessage(String[] message) {
		this.message = message;
	} 
}
