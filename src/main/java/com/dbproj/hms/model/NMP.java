package com.dbproj.hms.model;

public class NMP extends Employee {
    private Integer ID;
    private Integer empID;
    private String title;
    //generate getters and setters for this vun


    public NMP(Integer ID, Integer empID, String title) {
        this.ID = ID;
        this.empID = empID;
        this.title = title;
    }

    public NMP() {
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
