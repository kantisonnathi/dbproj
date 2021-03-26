package com.dbproj.hms.repository;

import com.dbproj.hms.model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DoctorRepository {
    /*@Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
    @Transactional(readOnly = true)
    Owner findById(@Param("id") Integer id);*/
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/dbproj";


    static final String USER = "root";
    static final String PASS = "";

    static Connection connection = null;
    static Statement statement = null;

    public DoctorRepository() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        statement = connection.createStatement();
    }


    public Doctor findByID(Integer ID) throws SQLException {
        String sql = "select * from doctor where DocID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, ID.toString());
        ResultSet result = preparedStatement.executeQuery();
        Doctor doctor = null;
        while (result.next()) {
            doctor = new Doctor(result.getInt("DocID"),
                    result.getInt("empID"),
                    result.getInt("visitation_fees"),
                    result.getString("speciality"),
                    result.getString("doc_type"));
        }
        return doctor;
    }

    public List<Doctor> findByName(String name) throws SQLException {
        String sql = "select * from doctor D where D.empID in (select EmpID from employee where EmpName=?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        ResultSet result = preparedStatement.executeQuery();
        List<Doctor> doctorList = new ArrayList<>();
        while (result.next()) {
             Doctor doctor = new Doctor(result.getInt("DocID"),
                    result.getInt("empID"),
                    result.getInt("visitation_fees"),
                    result.getString("speciality"),
                    result.getString("doc_type"));
             doctorList.add(doctor);
        }
        return doctorList;
    }


}
