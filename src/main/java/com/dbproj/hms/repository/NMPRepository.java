package com.dbproj.hms.repository;

import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.NMP;
import com.dbproj.hms.model.Transaction;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
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
        if (list.isEmpty()) {
            return null;
        }
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
        String query = "update employee set EmpName='" + nmp.getName() + "', username='" + nmp.getUsername() + "', gender='" + nmp.getGender() + "', salary=" + nmp.getSalary() + ", phno='" +
                nmp.getPhoneNumber() + "', email='" + nmp.getEmail() + "', address='" + nmp.getAddress() + "', authorization='" +
                nmp.getAuthorization() + "', verify=" + nmp.getVerify() + " where EmpID=" + nmp.getEmpID();
        jdbcTemplate.update(query);
        query = "update non_medical_professionals set title='" + nmp.getTitle() + "' where NP_id=" + nmp.getID();
        jdbcTemplate.update(query);

        return nmp;
    }

    public void updatetransaction(Integer empid) {
        String query="update transaction set empid='"+empid+"' where empid is NULL";
        jdbcTemplate.update(query);
    }

    public List<Transaction> gettransactions(Integer empid) {
        String query="select * from transaction where empid="+empid;

        return  jdbcTemplate.query(query,new TransactionRowMapper());
    }

    public List<NMP> listAllNMPs() {
        String query = "select * from non_medical_professionals";
        List<NMP> list = jdbcTemplate.query(query, new NMPRowMapper());
        /*List<NMP> finalList = new ArrayList<>();
        for (NMP nmp : list) {
            query = "select * from employee where EmpID=" + nmp.getEmpID();
            NMP temp = jdbcTemplate.query(query, new ResultSetExtractor<NMP>() {
                @Override
                public NMP extractData(@NotNull ResultSet resultSet) throws SQLException, DataAccessException {
                    NMP nmp = new NMP();
                    nmp.setEmpID(resultSet.getInt("EmpID"));
                    nmp.setName(resultSet.getString("EmpName"));
                    nmp.setUsername(resultSet.getString("username"));
                    nmp.setGender(resultSet.getString("gender").charAt(0));
                    nmp.setAddress(resultSet.getString("address"));
                    nmp.setSalary(resultSet.getInt("salary"));
                    nmp.setPhoneNumber(resultSet.getString("phno"));
                    nmp.setEmail(resultSet.getString("email"));
                    return nmp;
                }
            });
            if (temp != null) {
                temp.setID(nmp.getID());
                temp.setTitle(nmp.getTitle());
            }
            finalList.add(temp);
        }
        return finalList;*/
        return list;
    }
}
