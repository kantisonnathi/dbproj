package com.dbproj.hms.dao;

import com.dbproj.hms.model.Doctor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorRowMapper implements RowMapper<Doctor> {

    @Override
    public Doctor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Doctor(resultSet.getInt("DocID"),
               resultSet.getInt("empID"),
               resultSet.getInt("visitation_fees"),
               resultSet.getString("speciality"),
               resultSet.getString("doc_type"));
    }
}
