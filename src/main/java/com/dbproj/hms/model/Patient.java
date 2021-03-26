package com.dbproj.hms.model;

public class Patient {

    private Integer patientID;
    private String patientName;
    private Integer age;
    private String gender;
    private String medicalDetails;
    private Integer phno;
    private String email;
    private String address;


    public Patient() {
    }

    public Patient(Integer patientID, String patientName, Integer age, String gender, String medicalDetails, Integer phno, String email, String address){

        this.patientID = patientID;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.medicalDetails = medicalDetails;
        this.phno = phno;
        this.email = email;
        this.address = address;

    }

    public Integer getPatientID(){return patientID;}
    public void setPatientID(Integer patientID){this.patientID = patientID;}

    public String getPatientName(){return patientName;}
    public void setPatientName(String patientName){this.patientName = patientName;}

    public Integer getAge(){return age;}
    public void setAge(Integer age){this.age = age;}

    public String getGender(){return gender;}
    public void setGender(String gender){this.gender = gender;}

    public String getMedicalDetails(){return medicalDetails;}
    public void setMedicalDetails(String medicalDetails){this.medicalDetails = medicalDetails;}

    public Integer getPhno(){return phno;}
    public void setPhno(Integer phno){this.phno = phno;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}


    public String toString(){
        return "PatientID: " + patientID + ", Patient name: " + patientName + ", Age: " + age + ", Gender: " + gender;
        //will include the rest later according to need
    }


}
