package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Medicines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String mediname;

    private String strength;

    private String dosage;

    private String unit;

    private String days;

    private String remark;

    private String username;

    public Medicines() {}

    public Medicines(String mediname, String strength, String dosage,String unit ,String days, String remark,String username) {
        super();
        this.mediname = mediname;
        this.strength = strength;
        this.dosage = dosage;
        this.unit = unit;
        this.days = days;
        this.remark = remark;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMediname() {
        return mediname;
    }

    public void setMediname(String mediname) {
        this.mediname = mediname;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Medicines [id=" + id + ", mediname=" + mediname + ", strength=" + strength + ", dosage=" + dosage
                + ", unit=" + unit + ", days=" + days + ", remark=" + remark + ", username=" + username + "]";
    }




}
