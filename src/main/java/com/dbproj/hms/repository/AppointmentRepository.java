package com.dbproj.hms.repository;

import com.dbproj.hms.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Appointment save(Appointment appointment) {
        String query = "insert into appointment (docid, patientid, slot, complaints, diagnosis, appointment_date)" +
                " values (" + appointment.getDocID() + "," + appointment.getPatientID() + ", " + appointment.getSlot()
                 + ",'" + appointment.getComplaint() + "','" + appointment.getDiagnosis() + "', " + appointment.getDate()+")";
        jdbcTemplate.update(query);
        query = "select * from appointment where docid=" + appointment.getDocID() + " and slot=" + appointment.getSlot();
        List<Appointment> list = jdbcTemplate.query(query, new AppointmentRowMapper());
        return list.get(list.size()-1);
    }

}
