package com.dbproj.hms.repository;

import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.NMP;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NMPRowMapper implements RowMapper<NMP> {
    @Override
    public NMP mapRow(ResultSet resultSet, int i) throws SQLException {
        return new NMP(resultSet.getInt("NP_id"),
                resultSet.getInt("empid"),
                resultSet.getString("title"));
    }
}
