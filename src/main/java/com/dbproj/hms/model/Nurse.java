package com.dbproj.hms.model;

public class Nurse extends Employee {
    public Nurse(Integer nurseID, Integer empID) {
        this.nurseID = nurseID;
        this.EmpID = empID;
    }
    public Nurse() {

    }

    private Integer nurseID;
    private Integer EmpID;
    public Integer getNurseID() {
        return nurseID;
    }

    public void setNurseID(Integer ID) {
        this.nurseID = ID;
    }

    public Integer getEmpID() {
        return EmpID;
    }

    public void setEmpID(Integer empID) {
        EmpID = empID;
    }


    public String toString() {
        return "NurID: " + nurseID + ", empID: " + EmpID ;
    }

    public void setAuthorization() {
        String authorization="ROLE_USER";
        super.setAuthorization(authorization);
    }

}
