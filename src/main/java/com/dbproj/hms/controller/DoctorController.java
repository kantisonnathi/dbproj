package com.dbproj.hms.controller;

import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.repository.DoctorRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

public class DoctorController {

    DoctorRepository doctorRepository;

    public DoctorController() throws SQLException, ClassNotFoundException {
        doctorRepository = new DoctorRepository();
    }



}
