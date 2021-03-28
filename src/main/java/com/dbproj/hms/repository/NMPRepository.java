package com.dbproj.hms.repository;

import com.dbproj.hms.model.NMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class NMPRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public NMP findByID(Integer ID) throws DataAccessException, SQLException {
        String query = "select * from non_medical_professionals where NP_id="+ID.toString();
        List<NMP> list = jdbcTemplate.query(query,new NMPRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<NMP> findByName(String name) throws DataAccessException, SQLException {
        String query = "select * from non_medical_professionals where empid in (select EmpID from employee " +
                "where EmpName='" + name + "')";
        return jdbcTemplate.query(query, new NMPRowMapper());
    }

    public List<NMP> findByTitle(String title) throws DataAccessException, SQLException {
        String query = "select * from non_medical_professionals where title='" + title + "'";
        return jdbcTemplate.query(query,new NMPRowMapper());
    }


}
