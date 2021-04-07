package com.dbproj.hms.repository;

import com.dbproj.hms.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class TransactionRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public void addtransaction(Transaction transaction)
    {
        String query="insert into transaction(empid,patientid,totalcost) values('"+ transaction.getEmpid()+"','"+transaction.getPatientid()+"','"+ transaction.getTotalcost()+"')";
        jdbcTemplate.update(query);
    }
}
