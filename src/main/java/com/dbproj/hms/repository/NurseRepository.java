package com.dbproj.hms.repository;

import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.Nurse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
@Component
public class NurseRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate1;

    public Nurse findByID(@org.jetbrains.annotations.NotNull Integer ID) throws DataAccessException, SQLException {
        String query = "select * from nurse where nurseID=" + ID.toString();
        List<Nurse> listn = jdbcTemplate1.query(query, new NurseRowMapper());
        if (listn.isEmpty())
        {
            return null;
        }
        return listn.get(0);
    }

    public List<Nurse> findByName(String name) throws DataAccessException,SQLException{
        String query = "select * from nurse N where N.empID in (select EmpID" +
                " from employee where EmpName='" + name + "')";
        return jdbcTemplate1.query(query,new NurseRowMapper());
    }


    public void delete(Nurse nurse) throws DataAccessException,SQLException{
        String query="delete from employee  where empID="+nurse.getEmpID();
        jdbcTemplate1.update(query);
    }

    public void save(Nurse nurse) throws DataAccessException,SQLException {
        String query="insert into employee(EmpName,username,password,gender,salary,phno,email,address,authorization ,verify) values('"+ nurse.getName()+"','"+nurse.getUsername()+"','"+nurse.getPassword()+"','"+nurse.getGender()+"',"
                +nurse.getSalary()+",'"+nurse.getPhoneNumber()+"','"+nurse.getEmail()+"','"+nurse.getAddress()+"','"+nurse.getAuthorization()+"',"+nurse.getVerify()+")";
        jdbcTemplate1.update(query);
        query="select * from employee where username= '"+nurse.getUsername()+"'";
        List<Employee> l=jdbcTemplate1.query(query,new EmployeeRowMapper());
        Employee nur=l.get(l.size()-1);
        System.out.println(nurse.toString());
        query="Insert into nurse(empID) values("+nur.getID() +")";
        jdbcTemplate1.update(query);
    }

    public Nurse  update( Nurse nurse) throws DataAccessException {
        String query = "update employee set EmpName='" + nurse.getName() + "', username='" + nurse.getUsername() + "', gender='" + nurse.getGender() + "', salary=" + nurse.getSalary() + ", phno='" +
                nurse.getPhoneNumber() + "', email='" + nurse.getEmail() + "', address='" + nurse.getAddress() + "', authorization='" +
                nurse.getAuthorization() + "', verify=" + nurse.getVerify() + " where EmpID=" + nurse.getEmpID();
        jdbcTemplate1.update(query);
        return nurse;
    }

    public Nurse findByEmpID(Integer empID) {
        String query = "select * from nurse where empID=" + empID;
        List<Nurse> list = jdbcTemplate1.query(query, new NurseRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size()-1);
    }

    public List<Nurse> listAllNurses() {
        String query = "select * from nurse";
        return jdbcTemplate1.query(query, new NurseRowMapper());
    }
}