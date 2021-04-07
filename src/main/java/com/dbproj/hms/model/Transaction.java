package com.dbproj.hms.model;

import java.util.Date;

public class Transaction {
    int id;
    String docname;
    int visitationFees;
    Date appointmentdate;
    int empid;
    int patientid;

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public int getPatientid() {
        return patientid;
    }

    public void setPatientid(int patientid) {
        this.patientid = patientid;
    }

    public int getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(int totalcost) {
        this.totalcost = totalcost;
    }

    int totalcost;
    public Transaction(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
public Transaction(int empid,int patientid,int totalcost)
{
    this.empid=empid;
    this.patientid=patientid;
    this.totalcost=totalcost;
}
    public Transaction(int id, String docname, int visitationFees, Date appointmentdate)
    {
        this.id=id;
        this.docname=docname;
        this.visitationFees=visitationFees;
        this.appointmentdate=appointmentdate;

    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public int getVisitationFees() {
        return visitationFees;
    }

    public void setVisitationFees(int visitationFees) {
        this.visitationFees = visitationFees;
    }

    public Date getAppointmentdate() {
        return appointmentdate;
    }

    public void setAppointmentdate(Date appointmentdate) {
        this.appointmentdate = appointmentdate;
    }
//TODO: refer to transaction table and add all corresponding attributes, including getters and setters

}
