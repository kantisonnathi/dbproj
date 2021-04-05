package com.dbproj.hms.repository;

import com.dbproj.hms.model.Appointment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentRowMapper implements RowMapper<Appointment> {
    @Override
    public Appointment mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Appointment(resultSet.getInt("appointmentID"),
                resultSet.getInt("docid"),
                resultSet.getInt("patientid"),
                resultSet.getInt("slot"),
                resultSet.getString("complaints"),
                resultSet.getString("diagnosis"),
                resultSet.getDate("appointment_date"));
    }
}
