package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Appoinments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private String username;
	
	private String email;
	
	private String doctor;
	
	private String mobile;
	
	private String date;
	
	private String time;
	
	private String ampm;
	
	public Appoinments() {}

	public Appoinments(String name,String username ,String email, String doctor ,String mobile, String date, String time,String ampm) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.doctor = doctor;
		this.mobile = mobile;
		this.date = date;
		this.time = time;
		this.ampm = ampm;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getAmpm() {
		return ampm;
	}

	public void setAmpm(String ampm) {
		this.ampm = ampm;
	}

	@Override
	public String toString() {
		return "Appoinments [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", doctor="
				+ doctor + ", mobile=" + mobile + ", date=" + date + ", time=" + time + ", ampm=" + ampm + "]";
	}

	

}
