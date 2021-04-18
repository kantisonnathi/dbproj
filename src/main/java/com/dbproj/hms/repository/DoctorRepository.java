package com.dbproj.hms.repository;


import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.Nurse;
import com.dbproj.hms.model.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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
        Doctor ret = list.get(list.size()-1);
        Integer empID = list.get(0).getEmpID();
        query = "select * from employee where EmpID=" + empID.toString();
        List<Employee> list1 = jdbcTemplate.query(query, new EmployeeRowMapper());
        Employee emp = list1.get(list1.size()-1);
        ret = mapper(ret, emp);
        return ret;
    }

    public List<Doctor> findByName(String name) throws DataAccessException,SQLException{
        String query = "select * from doctor D where D.empID in (select EmpID" +
                " from employee where EmpName='" + name + "')";
        List<Doctor> list = jdbcTemplate.query(query,new DoctorRowMapper());
        List<Doctor> returnable = new ArrayList<>();
        for (Doctor doctor : list) {
            query = "select * from employee where EmpID="+doctor.getEmpID();
            List<Employee> list1 = jdbcTemplate.query(query, new EmployeeRowMapper());
            doctor = mapper(doctor, list1.get(list.size()-1));
            returnable.add(doctor);
        }
        return returnable;
    }

    public List<Doctor> findBySpeciality(String speciality) throws DataAccessException, SQLException {
        String query = "select * from doctor where speciality='" + speciality + "'";
        List<Doctor> list = jdbcTemplate.query(query,new DoctorRowMapper());
        List<Doctor> returnable = new ArrayList<>();
        for (Doctor doctor : list) {
            query = "select * from employee where EmpID="+doctor.getEmpID();
            List<Employee> list1 = jdbcTemplate.query(query, new EmployeeRowMapper());
            doctor = mapper(doctor, list1.get(0));
            returnable.add(doctor);
        }
        return returnable;
    }

    public List<Doctor> findDocWorkingWith(Integer ID) throws DataAccessException, SQLException{
        String query = "select * from doctor_nurse where NurseID=" + ID.toString();
        List<Integer> list = jdbcTemplate.query(query, new ResultSetExtractor<List<Integer>>() {
            @Override
            public List<Integer> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Integer> newList = new ArrayList<>();
                while(resultSet.next()){
                    newList.add(resultSet.getInt("DocID"));
                }
                return newList;
            }
        });
        List<Doctor> returnable = new ArrayList<>();
        for(Integer id : list){
            Doctor doctor = findByID(id);
            returnable.add(doctor);
        }

        return returnable;
    }

    public void addNurseToDoctor(Integer docid, Integer nurseid) {
        String query = "insert into doctor_nurse values (" + docid + "," + nurseid + ")";
        jdbcTemplate.update(query);
    }


    //To delete a doctor from the employee repository
    public void delete(Doctor doctor) throws DataAccessException,SQLException{
        String query="delete from employee  where empid="+doctor.getEmpID();
        jdbcTemplate.update(query);
    }

    //To add a doctor to the employee repository
    public Doctor save(Doctor doctor) throws DataAccessException,SQLException {
        String query="insert into employee(empname,username,password,gender,salary,phno,email,address,authorization ,verify,start_slot,end_slot,break_slot) values('"+ doctor.getName()+"','"+doctor.getUsername()+"','"+doctor.getPassword()+"','"+doctor.getGender()+"','"
        +doctor.getSalary()+"','"+doctor.getPhoneNumber()+"','"+doctor.getEmail()+"','"+doctor.getAddress()+"','"+doctor.getAuthorization()+"',1,'"+doctor.getStartSlot()+"','"+doctor.getEndSlot()+"','"+doctor.getBreaks()+"')";
        jdbcTemplate.update(query);
        query="select * from employee where username= '"+doctor.getUsername()+"'";
        List<Employee> l=jdbcTemplate.query(query,new EmployeeRowMapper());
        Employee doc=l.get(0);
        System.out.println(doctor.toString());
        query="Insert into doctor(empid,visitation_fees,speciality,doc_type) values('"+doc.getID()+"','"+doctor.getVisitationFees()+"','"+doctor.getSpeciality()+"','"+doctor.getDocType()+"')";
        jdbcTemplate.update(query);
        query = "select * from doctor where empID=" + doc.getID();
        List<Doctor> list = jdbcTemplate.query(query, new DoctorRowMapper());
        return list.get(list.size()-1);
    }
     // To update doctor value

    public Doctor update(Doctor doctor) throws DataAccessException {
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
        int start=doctor.getStartSlot();
        int  end= doctor.getEndSlot();
        int breaks=doctor.getBreaks();
       String q2="select * from time_slots where  slot not in(select slot from appointment where docid="+ doctor.getID() +") and  slot between "+start+" and "+end+" and slot!="+breaks+"";
        List<Slot> slot= jdbcTemplate.query(q2, new slotRowMapper());
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

    public List<Doctor> listAllDoctors() {
        String query = "select * from doctor";
        List<Doctor> list = jdbcTemplate.query(query,new DoctorRowMapper());
        List<Doctor> returnable = new ArrayList<>();
        for (Doctor doctor : list) {
            query = "select * from employee where EmpID="+doctor.getEmpID();
            List<Employee> list1 = jdbcTemplate.query(query, new EmployeeRowMapper());
            doctor = mapper(doctor, list1.get(0));
            returnable.add(doctor);
        }
        return returnable;
    }

    public Doctor mapper(Doctor ret, Employee emp) {
        ret.setEmail(emp.getEmail());
        ret.setName(emp.getName());
        ret.setUsername(emp.getUsername());
        ret.setGender(emp.getGender());
        ret.setSalary(emp.getSalary());
        ret.setPhoneNumber(emp.getPhoneNumber());
        ret.setAddress(emp.getAddress());
        ret.setAuthorization();
        ret.setVerify(emp.getVerify());
        ret.setStartSlot(emp.getStartSlot());
        ret.setEndSlot(emp.getEndSlot());
        ret.setBreaks(emp.getBreaks());
        return ret;
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
