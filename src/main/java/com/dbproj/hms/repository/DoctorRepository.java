package com.dbproj.hms.repository;


import com.dbproj.hms.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class DoctorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Doctor findByID(Integer ID) throws DataAccessException, SQLException {
        String query = "select * from doctor where DocID=" + ID.toString();
        List<Doctor> list = jdbcTemplate.query(query, new DoctorRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Doctor> findByName(String name) throws DataAccessException,SQLException{
        String query = "select * from doctor D where D.empID in (select EmpID" +
                " from employee where EmpName='" + name + "')";
        return jdbcTemplate.query(query,new DoctorRowMapper());
    }

    public List<Doctor> findBySpeciality(String speciality) throws DataAccessException, SQLException {
        String query = "select * from doctor where speciality='" + speciality + "'";
        return jdbcTemplate.query(query, new DoctorRowMapper());
    }
}














/*
package com.dbproj.hms.repository;

import com.dbproj.hms.controller.DoctorController;
import com.dbproj.hms.model.Doctor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DoctorRepository extends Repository{


    public DoctorRepository() throws ClassNotFoundException, SQLException {
        super();
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

    public List<Doctor> findBySpeciality(String speciality) throws SQLException {
        String sql = "select * from doctor where speciality=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,speciality);
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
*/
