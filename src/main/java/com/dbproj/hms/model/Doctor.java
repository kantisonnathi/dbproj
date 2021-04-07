package com.dbproj.hms.model;

public class Doctor extends Employee {
    public Doctor(Integer ID, Integer empID, Integer visitationFees,
                  String speciality, String docType) {
        this.ID = ID;
        this.EmpID = empID;
        this.visitationFees = visitationFees;
        this.speciality = speciality;
        this.docType = docType;
    }
    public Doctor() {

    }

    private Integer ID;
    private Integer EmpID;
    private Integer visitationFees;
    private String speciality;
    private String docType;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getEmpID() {
        return EmpID;
    }

    public void setEmpID(Integer empID) {
        EmpID = empID;
        super.setID(empID);
    }

    public Integer getVisitationFees() {
        return visitationFees;
    }

    public void setVisitationFees(Integer visitationFees) {
        this.visitationFees = visitationFees;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String toString() {
        return "DocID: " + ID + ", empID: " + EmpID + ", visitation fees: " +
                visitationFees + ", speciality: " + speciality + ", doc type: " + docType;
    }

    public void setAuthorization() {
        String authorization="ROLE_USER";
        super.setAuthorization(authorization);
    }

}
