package com.dbproj.hms.repository;

import com.dbproj.hms.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Transaction(resultSet.getInt("empid"),
                resultSet.getInt("patientid"),
                resultSet.getInt("totalcost"));

    }
}
