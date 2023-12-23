package com.example.demo.entity;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class lab_report {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String username;
	
	private String type;
	
    private String fileName;

	@Lob
	@Column(columnDefinition="LongBlob")
    private byte[] file;
    
    public lab_report() {}
    
	public lab_report(String username,String type,String fileName, byte[] file) {
		super();
		this.username = username;
		this.type = type;
		this.fileName = fileName;
		this.file = file;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "lab_report [id=" + id + ", username=" + username + ", type=" + type + ", fileName=" + fileName
				+ ", file=" + Arrays.toString(file) + "]";
	}

	

}
