package com.dbproj.hms.controller;

import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.repository.DoctorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.Map;

@Controller
public class DoctorController {

    DoctorRepository doctorRepository;

    public DoctorController() throws SQLException, ClassNotFoundException {
        doctorRepository = new DoctorRepository();
    }

    @GetMapping("/findDoctorByID")
    public String getByID(Map<String, Object> model) {
        //this is the get method;
        //post, we get the doctor and show page ez
        Integer id = 0;
        model.put("ID",id);
        return "doctor/findingDoctorID";
    }

    @PostMapping("/findDoctorByID")
    public String postID(Integer ID) {
        Doctor doctor = new Doctor();
        try {
            doctor = doctorRepository.findByID(ID);
        } catch (SQLException e) {
            return "system/error";
        }
        System.out.println("found doctor:" + doctor.toString());
        return "system/error" ;
    }

}
