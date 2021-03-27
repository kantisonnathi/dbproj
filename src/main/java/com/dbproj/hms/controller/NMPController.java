package com.dbproj.hms.controller;

import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.NMP;
import com.dbproj.hms.repository.EmployeeRepository;
import com.dbproj.hms.repository.NMPRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class NMPController {

    NMPRepository nmpRepository;
    EmployeeRepository employeeRepository;

    public NMPController() throws SQLException, ClassNotFoundException {
        nmpRepository = new NMPRepository();
        employeeRepository = new EmployeeRepository();
    }

    @GetMapping("/findNMPByID")
    public String getID(ModelMap modelMap) {
        Integer id = 0;
        modelMap.put("id",id);
        return "NMP/findingNMPByID";
    }

    @PostMapping("/findNMPByID")
    public String postID(Integer id) {
        return "redirect:/nmp/"+ id;
    }

    @GetMapping("/nmp/{nmpID}")
    public String getNMPPage(@PathVariable("nmpID") Integer nmpID, ModelMap modelMap) {
        NMP nmp;
        Employee employee;
        try {
            nmp = nmpRepository.findByID(nmpID);
            employee = employeeRepository.findByID(nmp.getEmpID());
        } catch (SQLException e) {
            return "system/error";
        }
        modelMap.put("nmp",nmp);
        modelMap.put("employee", employee);
        return "NMP/nmp";
    }

}
