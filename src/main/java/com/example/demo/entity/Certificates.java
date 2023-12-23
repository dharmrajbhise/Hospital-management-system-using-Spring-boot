package com.example.demo.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Certificates {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String patientName;

	private String username;

	private String doctor;

	private String certificateName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate issueDate;

	public Certificates() {}

	public Certificates(String patientName, String username,String doctor ,String certificateName, LocalDate issueDate) {
		super();
		this.patientName = patientName;
		this.username = username;
		this.doctor = doctor;
		this.certificateName = certificateName;
		this.issueDate = issueDate;
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

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	@Override
	public String toString() {
		return "Certificates [id=" + id + ", patientName=" + patientName + ", username=" + username + ", doctor="
				+ doctor + ", certificateName=" + certificateName + ", issueDate=" + issueDate + "]";
	}
	
	
	
	
	
	

}
