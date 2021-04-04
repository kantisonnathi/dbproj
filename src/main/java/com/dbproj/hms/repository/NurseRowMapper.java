package com.dbproj.hms.repository;

import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.Nurse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NurseRowMapper implements RowMapper<Nurse> {
    @Override
    public Nurse mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Nurse(resultSet.getInt("nurseID"),
                resultSet.getInt("empID"));
    }
}

