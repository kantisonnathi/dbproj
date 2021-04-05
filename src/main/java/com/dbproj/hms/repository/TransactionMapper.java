package com.dbproj.hms.repository;

import com.dbproj.hms.model.Slot;
import com.dbproj.hms.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Transaction(resultSet.getInt("appointmentID"),
                resultSet.getString("EmpName"),
                resultSet.getInt("visitation_fees"),
                resultSet.getDate("appointment_date"));
    }

}

