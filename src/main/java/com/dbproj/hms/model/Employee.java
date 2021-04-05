package com.dbproj.hms.model;


import java.io.Serializable;

public class Employee implements Serializable {

    static Integer IDValue = 1;

    private Integer ID;
    private String name;
    private String username;
    private String password;
    private Character gender;
    private Integer salary;
    private String phoneNumber;
    private String email;
    private String address;
    private String authorization;
    private int verify;

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }

    public Employee() {
        this.ID = IDValue++;
    }

    public Employee(Integer ID, String name, String username, String password, Character gender, Integer salary, String phoneNumber, String email, String address, String authorization) {
        this.ID = ID;
        this.name = name;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.authorization = authorization;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
