package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Receipt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String username;

	private String address;

	private String date;

	private String doctor;

	private String hospital;

	private String services;

	public Receipt() {}

	public Receipt(String name, String username, String address, String date, String doctor, String hospital ,String services) {
		super();
		this.name = name;
		this.username = username;
		this.address = address;
		this.date = date;
		this.doctor = doctor;
		this.hospital = hospital;
		this.services = services;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return "Receipt [id=" + id + ", name=" + name + ", username=" + username + ", address=" + address + ", date="
				+ date + ", doctor=" + doctor + ", hospital=" + hospital + ", services=" + services + "]";
	}

	

}
