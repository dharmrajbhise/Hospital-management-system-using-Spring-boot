package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Doctors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String FirstName;

    private String LastName;

    private String hospital;

    private String mobile;

    private String hospitalAddress;

    public Doctors(){}

    public Doctors(long id,String FirstName,String LastName,String hospital,String mobile,String hospitalAddress) {
        this.id = id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.hospital = hospital;
        this.mobile = mobile;
        this.hospitalAddress = hospitalAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    @Override
    public String toString() {
        return "Doctors{" +
                "id=" + id +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", hospital='" + hospital + '\'' +
                ", mobile='" + mobile + '\'' +
                ", hospitalAddress='" + hospitalAddress + '\'' +
                '}';
    }
}
