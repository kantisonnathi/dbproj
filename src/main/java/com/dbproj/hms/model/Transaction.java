package com.dbproj.hms.model;

import java.util.Date;

public class Transaction {
    String docname;
    int visitationFees;
    Date appointmentdate;
    public Transaction(){

    }
    public Transaction(String docname,int visitationFees,Date appointmentdate)
    {
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
