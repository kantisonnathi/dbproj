package com.dbproj.hms.repository;

import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.NMP;
import com.dbproj.hms.model.Nurse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class NurseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate1;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Nurse findByID(@org.jetbrains.annotations.NotNull Integer ID) throws DataAccessException, SQLException {
        String query = "select * from nurse where nurseID=" + ID.toString();
        List<Nurse> list = jdbcTemplate1.query(query, new NurseRowMapper());
        if (list.isEmpty()) {
            return null;
        }
        query = "select * from employee where EmpID=" + list.get(list.size()-1).getEmpID();
        List<Employee> employees = jdbcTemplate1.query(query, new EmployeeRowMapper());
        Nurse ret = mapper(list.get(list.size()-1),employees.get(list.size()-1));
        return ret;
    }

    public List<Nurse> findByName(String name) throws DataAccessException,SQLException{
        String query = "select * from nurse N where N.empID in (select EmpID" +
                " from employee where EmpName='" + name + "')";
        List<Nurse> list = jdbcTemplate1.query(query, new NurseRowMapper());
        List<Nurse> returnable = new ArrayList<>();
        for (Nurse nmp : list) {
            query = "select * from employee where EmpID=" + nmp.getEmpID();
            List<Employee> emps = jdbcTemplate1.query(query, new EmployeeRowMapper());
            nmp = mapper(nmp, emps.get(emps.size()-1));
            returnable.add(nmp);
        }
        return returnable;
    }

    public List<Nurse> findNursesWorkingUnder(Integer ID) throws DataAccessException,SQLException{
        String query = "select * from doctor_nurse where DocID=" + ID.toString();
        List<Integer> list = jdbcTemplate1.query(query, new ResultSetExtractor<List<Integer>>() {
            @Override
            public List<Integer> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Integer> newList = new ArrayList<>();
                while(resultSet.next()){
                    newList.add(resultSet.getInt("NurseID"));
                }
                return newList;
            }
        });

        List<Nurse> returnable = new ArrayList<>();
        for(Integer id : list){
            Nurse nurse = findByID(id);
            returnable.add(nurse);
        }

        return returnable;

    }


    public void delete(Nurse nurse) throws DataAccessException,SQLException{
        String query="delete from employee  where empID="+nurse.getEmpID();
        jdbcTemplate1.update(query);
    }

    public void save(Nurse nurse) throws DataAccessException,SQLException {
        this.employeeRepository.save(nurse);
        String query="select * from employee where username= '"+nurse.getUsername()+"'";
        List<Employee> l=jdbcTemplate1.query(query,new EmployeeRowMapper());
        Employee nur=l.get(l.size()-1);
        System.out.println(nurse.toString());
        query="Insert into nurse(empID) values("+nur.getID() +")";
        jdbcTemplate1.update(query);
    }

    public Nurse update(Nurse nurse) throws DataAccessException {
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
        List<Nurse> list = jdbcTemplate1.query(query, new NurseRowMapper());
        List<Nurse> returnable = new ArrayList<>();
        for (Nurse nmp : list) {
            query = "select * from employee where EmpID=" + nmp.getEmpID();
            List<Employee> emps = jdbcTemplate1.query(query, new EmployeeRowMapper());
            nmp = mapper(nmp, emps.get(emps.size()-1));
            returnable.add(nmp);
        }
        return returnable;
    }

    public Nurse mapper(Nurse ret, Employee emp) {
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