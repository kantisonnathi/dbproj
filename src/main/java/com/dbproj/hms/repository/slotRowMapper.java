package com.dbproj.hms.repository;

import com.dbproj.hms.model.Slot;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class slotRowMapper implements RowMapper<Slot> {
    @Override
    public Slot mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Slot(resultSet.getInt("slot"),
                resultSet.getTime("start_time"),
                resultSet.getTime("end_time"));
    }

}
