package com.dbproj.hms.repository;

import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.NMP;
import com.dbproj.hms.model.Nurse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class EmployeeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NurseRepository nurseRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    NMPRepository nmpRepository;


    public Employee findByID(Integer ID) throws DataAccessException, SQLException {
        String query = "select * from employee where EmpID=" + ID.toString();
        List<Employee> list = jdbcTemplate.query(query,new EmployeeRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public void save(Employee employee) throws DataAccessException {
        String query="insert into employee(empname,username,password,gender,salary,phno,email,address,authorization ,verify, start_slot, end_slot, break_slot)" +
                " values('" + employee.getName() + "','" + employee.getUsername() + "','" + employee.getPassword() + "','" +
                employee.getGender() + "','" + employee.getSalary() + "','" + employee.getPhoneNumber() + "','" + employee.getEmail()
                + "','" + employee.getAddress() + "','" + employee.getAuthorization() + "'," + employee.getVerify() + "," + employee.getStartSlot() + ","
        + employee.getEndSlot() + ", " + employee.getBreaks() + ")";
        jdbcTemplate.update(query);
    }

    public void delete(Employee employee) throws DataAccessException {
        String query = "delete from employee where empid=" + employee.getID();
        jdbcTemplate.update(query);
    }

    public Employee update(Employee nmp) {
        String query = "update employee set EmpName='" + nmp.getName() + "', username='" + nmp.getUsername() + "', password='"
                + nmp.getPassword() + "', gender='" + nmp.getGender() + "', salary=" + nmp.getSalary() + ", phno='" +
                nmp.getPhoneNumber() + "', email='" + nmp.getEmail() + "', address='" + nmp.getAddress() + "', authorization='" +
                nmp.getAuthorization() + "', verify=" + nmp.getAuthorization() + " where EmpID=" + nmp.getID();
        jdbcTemplate.update(query);
        return nmp;
    }

    public Boolean isEmpOnLeave(Integer empID, Date date) {
        String query = "select empID from emp_on_leave where date='" + date + "' and empID=" + empID;
        List<Integer> list = jdbcTemplate.query(query, new ResultSetExtractor<List<Integer>>() {
            @Override
            public List<Integer> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Integer> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(resultSet.getInt("empID"));
                }
                return list;
            }
        });
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    public Employee findByUsername(String username) {
        String query = "select * from employee where username='" + username + "'";
        List<Employee> list = jdbcTemplate.query(query, new EmployeeRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size()-1);
    }

    public String type(Employee employee) {
        Doctor doctor = this.doctorRepository.findByEmpID(employee.getID());
        if (doctor != null) {
            return "doctor";
        }
        Nurse nurse = this.nurseRepository.findByEmpID(employee.getID());
        if (nurse != null) {
            return "nurse";
        }
        NMP nmp = this.nmpRepository.findByEmpID(employee.getID());
        if (nmp != null) {
            return "nmp";
        }
        return "null";
    }

    public List<Employee> listAllEmployees() {
        String query = "select * from employee";
        return jdbcTemplate.query(query, new EmployeeRowMapper());
    }

    public void putHoliday(Integer empid, Date date) {
        String query = "insert into emp_on_leave (empID, date) values (?,?)";
        jdbcTemplate.update(query,empid,date);
    }

    /*public EmployeeRepository() throws ClassNotFoundException, SQLException {
        super();
    }

    public Employee findByID(Integer ID) throws SQLException {
        String sql = "select * from employee where EmpID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, ID.toString());
        ResultSet result = preparedStatement.executeQuery();
        Employee employee = null;
        while (result.next()) {
            employee = new Employee(result.getInt("EmpID"),
                    result.getString("EmpName"),
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("gender").charAt(0),
                    result.getInt("salary"),
                    result.getString("phno"),
                    result.getString("email"),
                    result.getString("address"),
                    result.getString("authorization"));
        }
        return employee;
    }*/


}
