package com.dbproj.hms.controller;

import com.dbproj.hms.model.Doctor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {

    @GetMapping("/")
    public String returnDocQueryPage() {
        return "main";
    }
}
