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

    public void delete(Patient patient) throws DataAccessException, SQLException {
        String query = "delete from patient where patientID=" + patient.getPatientID();
        jdbcTemplate.update(query);
    }

    public Patient save(Patient patient) throws DataAccessException, SQLException {
        String query = "insert into patient (patientName, age, gender, medicalDetails, phno, email" +
                ", address) values ('" + patient.getPatientName() + "'," + patient.getAge() + ", '" + patient.getGender()
                + "', '" + patient.getMedicalDetails() + "', '" + patient.getPhno() + "', '" + patient.getEmail() + "')";
        jdbcTemplate.update(query);
        return patient; //set its ID before returning it. see what to query the patient table with.x
    }
}
