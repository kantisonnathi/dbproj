package com.dbproj.hms.dao;

import com.dbproj.hms.model.Doctor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class DoctorDao {

    private JdbcTemplate jdbcTemplate;

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Doctor findByID(Integer ID) throws DataAccessException, SQLException {
        String query = "select * from doctor where DocID=" + ID.toString();
        List<Doctor> list =  jdbcTemplate.query(query, new DoctorRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
