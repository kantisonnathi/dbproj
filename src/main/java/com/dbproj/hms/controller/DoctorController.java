package com.dbproj.hms.controller;

import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.repository.DoctorRepository;
import com.dbproj.hms.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.Map;

@Controller
public class DoctorController {

    DoctorRepository doctorRepository;
    EmployeeRepository employeeRepository;

    public DoctorController() throws SQLException, ClassNotFoundException {
        doctorRepository = new DoctorRepository();
        employeeRepository = new EmployeeRepository();
    }

    @GetMapping("/findDoctorByID")
    public String getByID(ModelMap model) {
        //this is the get method;
        //post, we get the doctor and show page ez
        Integer id = 0;
        model.put("id",id);
        return "doctor/findingDoctorID";
    }

    @PostMapping("/findDoctorByID")
    public String postID(Integer id) {
        Doctor doctor;
        try {
            doctor = doctorRepository.findByID(id);
        } catch (SQLException e) {
            return "system/error";
        }
        //System.out.println("found doctor:" + doctor.toString());
        return "redirect:/doc/" + id ;
    }

    @GetMapping("/doc/{docID}")
    public String getDocPage(@PathVariable("docID") Integer DocID, ModelMap model) {
        Doctor doctor;
        Employee employee;
        try {
            doctor = doctorRepository.findByID(DocID);
            employee = employeeRepository.findByID(doctor.getEmpID());
        } catch (SQLException e) {
            return "system/error";
        }
        model.put("doctor",doctor);
        model.put("employee", employee);
        return "doctor/doctor";
    }

}
