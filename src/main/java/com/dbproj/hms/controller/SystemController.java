package com.dbproj.hms.controller;

import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.NMP;
import com.dbproj.hms.model.Nurse;
import com.dbproj.hms.repository.DoctorRepository;
import com.dbproj.hms.repository.EmployeeRepository;
import com.dbproj.hms.repository.NMPRepository;
import com.dbproj.hms.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@CrossOrigin
public class SystemController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    NurseRepository nurseRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    NMPRepository nmpRepository;


    @GetMapping("/")
    public String returnDocQueryPage(Principal principal, ModelMap modelMap) {
        return "main";
    }

    @GetMapping("/myUserDetails")
    public String returnUserDetails(Principal principal, ModelMap modelMap) {
        Employee employee = this.employeeRepository.findByUsername(principal.getName());
        Doctor doctor = this.doctorRepository.findByEmpID(employee.getID());
        if (doctor != null) {
            return "redirect:/doc/" + doctor.getID();
        }
        Nurse nurse = this.nurseRepository.findByEmpID(employee.getID());
        if (nurse != null) {
            return "redirect:/nur/" + nurse.getID();
        }
        NMP nmp = this.nmpRepository.findByEmpID(employee.getID());
        if (nmp != null) {
            return "redirect:/nmp/" + nmp.getID();
        }
        String error =  "You do not seem to be of any type, sorry no details :(";
        modelMap.put("title",error);
        return "system/customError";
    }
    @GetMapping("/makepayment")
    public String capturenmp(Principal principal,ModelMap modelMap)
    {
        Employee employee=this.employeeRepository.findByUsername(principal.getName());
        NMP nmp=this.nmpRepository.findByEmpID(employee.getID());
        if(nmp==null)
        {
            return "system/Authorization error";
        }
        else
        {
            return "redirect:/makepayment/"+ nmp.getID();
        }
    }

    @GetMapping("/error")
    public String error()
    {
        return "system/Authorization error";
    }
}
