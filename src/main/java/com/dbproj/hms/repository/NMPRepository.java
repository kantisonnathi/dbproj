package com.dbproj.hms.repository;

import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.NMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class NMPRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EmployeeRepository employeeRepository;

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

    public void delete(NMP nmp) throws DataAccessException, SQLException {
        String query = "delete from employee where empID="+nmp.getEmpID();
        jdbcTemplate.update(query);
    }

    public NMP findByEmpID(Integer empID) throws DataAccessException{
        String query = "select * from non_medical_professionals where empid="+ empID;
        List<NMP> list = jdbcTemplate.query(query, new NMPRowMapper());
        return list.get(list.size()-1);
    }

    public NMP save(NMP nmp) throws DataAccessException, SQLException {
        employeeRepository.save(nmp);
        String query="select * from employee where username= '" + nmp.getUsername() + "'";
        List<Employee> l=jdbcTemplate.query(query,new EmployeeRowMapper());
        Employee nmpEmp=l.get(0);
        query="Insert into non_medical_professionals(empid,title) values("+ nmpEmp.getID() + ",'" + nmp.getTitle()+"')";
        jdbcTemplate.update(query);
        //now we return the same object but with the ID allocated.
        nmp = findByEmpID(nmpEmp.getID());
        return nmp;
    }

    public NMP update(NMP nmp) throws DataAccessException {
        String query = "update employee set EmpName='" + nmp.getName() + "', username='" + nmp.getUsername() + "', password='"
                + nmp.getPassword() + "', gender='" + nmp.getGender() + "', salary=" + nmp.getSalary() + ", phno='" +
                nmp.getPhoneNumber() + "', email='" + nmp.getEmail() + "', address='" + nmp.getAddress() + "', authorization='" +
                nmp.getAuthorization() + "', verify=" + nmp.getVerify() + " where EmpID=" + nmp.getEmpID();
        jdbcTemplate.update(query);
        query = "update non_medical_professionals set title='" + nmp.getTitle() + "' where NP_id=" + nmp.getID();
        jdbcTemplate.update(query);

        return nmp;
    }


}
