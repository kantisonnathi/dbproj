package com.dbproj.hms.repository;

import com.dbproj.hms.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class PatientRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Patient findByID(Integer patientID) throws DataAccessException, SQLException {
        String sql = "select * from patient where patientID=" + patientID.toString();
        List<Patient> list = jdbcTemplate.query(sql, new PatientRowMapper());
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public List<Patient> findByName(String patientName) throws DataAccessException, SQLException {
        String sql = "select * from patient where patientName='" + patientName + "'";
        return jdbcTemplate.query(sql, new PatientRowMapper());
    }
}
