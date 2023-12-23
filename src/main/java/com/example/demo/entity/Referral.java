package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Referral {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String patientName;

	private String username;

	private String doctor;

	private String hospital;

	private String date;

	private String recipientName;

	private String recipientAddress;

	private String recipientContact;

	private String subject;

	public Referral() {}

	public Referral(String patientName, String username, String doctor,String hospital,String date ,String recipientName, String recipientAddress,
					String recipientContact, String subject) {
		super();
		this.patientName = patientName;
		this.username = username;
		this.doctor = doctor;
		this.hospital = hospital;
		this.date = date;
		this.recipientName = recipientName;
		this.recipientAddress = recipientAddress;
		this.recipientContact = recipientContact;
		this.subject = subject;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getRecipientAddress() {
		return recipientAddress;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	public String getRecipientContact() {
		return recipientContact;
	}

	public void setRecipientContact(String recipientContact) {
		this.recipientContact = recipientContact;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	@Override
	public String toString() {
		return "Referral [id=" + id + ", patientName=" + patientName + ", username=" + username + ", doctor=" + doctor
				+ ", hospital=" + hospital + ", date=" + date + ", recipientName=" + recipientName
				+ ", recipientAddress=" + recipientAddress + ", recipientContact=" + recipientContact + ", subject="
				+ subject + "]";
	}

}
