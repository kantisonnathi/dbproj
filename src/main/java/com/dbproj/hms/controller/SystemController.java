package com.dbproj.hms.controller;

import com.dbproj.hms.model.Doctor;
import org.springframework.web.bind.annotation.GetMapping;

public class SystemController {
    @GetMapping("/findDocBy")
    public String returnDocQueryPage() {
        Doctor doctor = new Doctor();
        return "doctor/findingDoctor";
    }
}
