package com.dbproj.hms.repository;

import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.slot;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class slotRowMapper implements RowMapper<slot> {
    @Override
    public slot  mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new slot(resultSet.getInt("slot"),
                resultSet.getTime("start_time"),
                resultSet.getTime("end_time"));
    }

}
