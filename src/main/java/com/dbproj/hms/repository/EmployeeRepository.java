package com.dbproj.hms.repository;

import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;


@Component
public class EmployeeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public Employee findByID(Integer ID) throws DataAccessException, SQLException {
        String query = "select * from employee where EmpID=" + ID.toString();
        List<Employee> list = jdbcTemplate.query(query,new EmployeeRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /*public EmployeeRepository() throws ClassNotFoundException, SQLException {
        super();
    }

    public Employee findByID(Integer ID) throws SQLException {
        String sql = "select * from employee where EmpID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, ID.toString());
        ResultSet result = preparedStatement.executeQuery();
        Employee employee = null;
        while (result.next()) {
            employee = new Employee(result.getInt("EmpID"),
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
        return employee;
    }*/


}
