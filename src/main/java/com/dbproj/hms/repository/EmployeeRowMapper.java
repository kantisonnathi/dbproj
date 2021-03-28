package com.dbproj.hms.repository;

import com.dbproj.hms.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet result, int rowNum) throws SQLException {
        return new Employee(result.getInt("EmpID"),
                result.getString("EmpName"),
                result.getString("username"),
                result.getString("password"),
                result.getString("gender").charAt(0),
                result.getInt("salary"),
                result.getString("phno"),
                result.getString("email"),
                result.getString("address"),
                result.getString("authorization"));
    }
}
