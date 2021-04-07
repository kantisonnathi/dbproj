package com.dbproj.hms.repository;


import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class DoctorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Doctor findByID(Integer ID) throws DataAccessException, SQLException {
        String query = "select * from doctor where DocID=" + ID.toString();
        List<Doctor> list = jdbcTemplate.query(query, new DoctorRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Doctor> findByName(String name) throws DataAccessException,SQLException{
        String query = "select * from doctor D where D.empID in (select EmpID" +
                " from employee where EmpName='" + name + "')";
        return jdbcTemplate.query(query,new DoctorRowMapper());
    }

    public List<Doctor> findBySpeciality(String speciality) throws DataAccessException, SQLException {
        String query = "select * from doctor where speciality='" + speciality + "'";
        return jdbcTemplate.query(query, new DoctorRowMapper());
    }

    //To delete a doctor from the employee repository
    public void delete(Doctor doctor) throws DataAccessException,SQLException{
        String query="delete from employee  where empid="+doctor.getEmpID();
        jdbcTemplate.update(query);
    }

    //To add a doctor to the employee repository
    public void save(Doctor doctor) throws DataAccessException,SQLException {
        String query="insert into employee(empname,username,password,gender,salary,phno,email,address,authorization ,verify) values('"+ doctor.getName()+"','"+doctor.getUsername()+"','"+doctor.getPassword()+"','"+doctor.getGender()+"','"
        +doctor.getSalary()+"','"+doctor.getPhoneNumber()+"','"+doctor.getEmail()+"','"+doctor.getAddress()+"','"+doctor.getAuthorization()+"',1)";
        jdbcTemplate.update(query);
        query="select * from employee where username= '"+doctor.getUsername()+"'";
        List<Employee> l=jdbcTemplate.query(query,new EmployeeRowMapper());
        Employee doc=l.get(0);
        System.out.println(doctor.toString());
        query="Insert into doctor(empid,visitation_fees,speciality,doc_type) values('"+doc.getID()+"','"+doctor.getVisitationFees()+"','"+doctor.getSpeciality()+"','"+doctor.getDocType()+"')";
        jdbcTemplate.update(query);
    }
     // To update doctor value

    public Doctor  update( Doctor doctor) throws DataAccessException {
        String query = "update employee set EmpName='" + doctor.getName() + "', username='" + doctor.getUsername() + "', gender='" + doctor.getGender() + "', salary=" + doctor.getSalary() + ", phno='" +
                doctor.getPhoneNumber() + "', email='" + doctor.getEmail() + "', address='" + doctor.getAddress() + "', authorization='" +
                doctor.getAuthorization() + "', verify=" + doctor.getVerify() + " where EmpID=" + doctor.getEmpID();
        jdbcTemplate.update(query);
        query = "update doctor set visitation_fees='" + doctor.getVisitationFees() + "' where docid=" + doctor.getID();
        jdbcTemplate.update(query);
         query="update doctor set speciality='"+doctor.getSpeciality()+"' where docid="+ doctor.getID();
         jdbcTemplate.update(query);
         query="update doctor set doc_type='"+doctor.getDocType()+"'where docid="+ doctor.getID();
         jdbcTemplate.update(query);
        return doctor;
    }
    //to get slot numbers of  appointment numbers when docid is given
    public List<Slot> getslots(Doctor doctor) throws DataAccessException{
        String query="select * from time_slots where slot not in(select slot from appointment where docid="+ doctor.getID() +")";
        List<Slot> slot= jdbcTemplate.query(query, new slotRowMapper());
        return slot;
    }

    public Doctor findByEmpID(Integer empID) {
        String query = "select * from doctor where empID=" + empID;
        List<Doctor> list = jdbcTemplate.query(query, new DoctorRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size()-1);
    }
}














/*
package com.dbproj.hms.repository;

import com.dbproj.hms.controller.DoctorController;
import com.dbproj.hms.model.Doctor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DoctorRepository extends Repository{


    public DoctorRepository() throws ClassNotFoundException, SQLException {
        super();
    }

    public Doctor findByID(Integer ID) throws SQLException {
        String sql = "select * from doctor where DocID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, ID.toString());
        ResultSet result = preparedStatement.executeQuery();
        Doctor doctor = null;
        while (result.next()) {
            doctor = new Doctor(result.getInt("DocID"),
                    result.getInt("empID"),
                    result.getInt("visitation_fees"),
                    result.getString("speciality"),
                    result.getString("doc_type"));
        }
        return doctor;
    }

    public List<Doctor> findByName(String name) throws SQLException {
        String sql = "select * from doctor D where D.empID in (select EmpID from employee where EmpName=?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        ResultSet result = preparedStatement.executeQuery();
        List<Doctor> doctorList = new ArrayList<>();
        while (result.next()) {
             Doctor doctor = new Doctor(result.getInt("DocID"),
                    result.getInt("empID"),
                    result.getInt("visitation_fees"),
                    result.getString("speciality"),
                    result.getString("doc_type"));
             doctorList.add(doctor);
        }
        return doctorList;
    }

    public List<Doctor> findBySpeciality(String speciality) throws SQLException {
        String sql = "select * from doctor where speciality=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,speciality);
        ResultSet result = preparedStatement.executeQuery();
        List<Doctor> doctorList = new ArrayList<>();
        while (result.next()) {
            Doctor doctor = new Doctor(result.getInt("DocID"),
                    result.getInt("empID"),
                    result.getInt("visitation_fees"),
                    result.getString("speciality"),
                    result.getString("doc_type"));
            doctorList.add(doctor);
        }
        return doctorList;
    }


}
*/
