package com.dbproj.hms.repository;

import com.dbproj.hms.model.Patient;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientRowMapper implements RowMapper<Patient>{

    @Override
    public Patient mapRow(ResultSet resultSet, int rowNum) throws SQLException{
        return new Patient(resultSet.getInt("patientID"),
                resultSet.getString("patientName"),
                resultSet.getInt("age"),
                resultSet.getString("gender"),
                resultSet.getString("medicalDetails"),
                resultSet.getString("phno"),
                resultSet.getString("email"),
                resultSet.getString("address"));
    }
}
