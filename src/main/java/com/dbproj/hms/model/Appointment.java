package com.dbproj.hms.model;

import java.io.Serializable;

public class Appointment implements Serializable {
    private Integer ID;

    public Appointment(Integer ID,Integer docID, Integer patientID, Integer slot, String complaint, String diagnosis, String date) {
        this.ID = ID;
        this.docID = docID;
        this.patientID = patientID;
        this.slot = slot;
        this.complaint = complaint;
        this.diagnosis = diagnosis;
        this.date = date;
    }
    public Appointment() {

    }

    private Integer docID;
    private Integer patientID;
    private Integer slot;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getDocID() {
        return docID;
    }

    public void setDocID(Integer docID) {
        this.docID = docID;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    private String complaint;
    private String diagnosis;

}
