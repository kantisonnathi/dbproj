package com.dbproj.hms.controller;

import com.dbproj.hms.dao.DoctorDao;
import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.repository.DoctorRepository;
import com.dbproj.hms.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

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
        //System.out.println("found doctor:" + doctor.toString());
        return "redirect:/doc/" + id ;
    }

    @GetMapping("/findDoctorByName")
    public String getName(ModelMap model) {
        String name = "";
        model.put("name",name);
        return "doctor/findingDocByName";
    }

    /*@PostMapping("/findDoctorByName")
    public String postName(String name, ModelMap model) {
        List<Doctor> doctors;
        try {
            doctors = doctorRepository.findByName(name);
        } catch (SQLException e) {
            return "system/error";
        }
        model.put("doctors",doctors);
        model.put("title","Querying By Name");
        return "doctor/listResults";
    }*/

    @GetMapping("/findDoctorBySpeciality")
    public String getSpeciality(ModelMap modelMap) {
        String speciality = "";
        modelMap.put("speciality",speciality);
        return "doctor/findingDocBySpeciality";
    }

   /* @PostMapping("/findDoctorBySpeciality")
    public String postSpeciality(String speciality, ModelMap modelMap) {
        List<Doctor> doctors;
        try {
            doctors = doctorRepository.findBySpeciality(speciality);
        } catch (SQLException e) {
            return "system/error";
        }
        modelMap.put("doctors",doctors);
        modelMap.put("title","Querying By Speciality");
        return "doctor/listResults";
    }*/



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
