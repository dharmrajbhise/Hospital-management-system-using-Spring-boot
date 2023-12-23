package com.example.demo.entity;

import javax.persistence.*;

@Entity
public class HealthChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String date;

    private String doctor;

    private String complaints;

    private String observations;

    @Column(name = "Lab_Result")
    private String labresults;

    @Column(name = "Diagnosis")
    private String diagnosis;

    public HealthChart(){}

    public HealthChart(long id,String username,String date,String doctor,String complaints,String observations,String labresults,String diagnosis) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.doctor = doctor;
        this.complaints = complaints;
        this.observations = observations;
        this.labresults = labresults;
        this.diagnosis = diagnosis;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getLabresults() {
        return labresults;
    }

    public void setLabresults(String labresults) {
        this.labresults = labresults;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "HealthChart{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", date='" + date + '\'' +
                ", doctor='" + doctor + '\'' +
                ", complaints='" + complaints + '\'' +
                ", observations='" + observations + '\'' +
                ", labresults='" + labresults + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }
}
