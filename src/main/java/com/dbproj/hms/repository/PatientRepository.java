package com.dbproj.hms.repository;

import com.dbproj.hms.model.Patient;
import org.springframework.security.core.parameters.P; //wont need this either

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/dbproj";

    static final String USER = "root"; //patient wont have username and pass
    static final String PASS = ""; //need to remove this and figure out connection

    static Connection connection = null;
    static Statement statement = null;

    public PatientRepository() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        statement = connection.createStatement();
    }

    public Patient findByID(Integer patientID) throws SQLException{
        String sql = "select * from patient where patientID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, patientID.toString());
        ResultSet result = preparedStatement.executeQuery();
        Patient patient = null;

        while(result.next()){
            patient = new Patient(result.getInt("patientID"),
                    result.getString("patientName"),
                    result.getInt("age"),
                    result.getString("gender"),
                    result.getString("medicalDetails"),
                    result.getInt("phno"),
                    result.getString("email"),
                    result.getString("address"));
        }
        return patient;
    }

    public List<Patient> findByName(String patientName) throws SQLException{
        String sql = "select * from patient where patientName=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,patientName);
        ResultSet result = preparedStatement.executeQuery();
        List<Patient> patientList = new ArrayList<>();
        while(result.next()){
            Patient patient = new Patient(result.getInt("patientID"),
                    result.getString("patientName"),
                    result.getInt("age"),
                    result.getString("gender"),
                    result.getString("medicalDetails"),
                    result.getInt("phno"),
                    result.getString("email"),
                    result.getString("address"));
        }
        return patientList;
    }
}
