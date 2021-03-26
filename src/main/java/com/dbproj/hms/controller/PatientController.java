package com.dbproj.hms.controller;

import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.Patient;
import com.dbproj.hms.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.sql.SQLException;
import java.util.List;

@Controller
public class PatientController {

    PatientRepository patientRepository;

    public PatientController() throws SQLException, ClassNotFoundException{
        patientRepository = new PatientRepository();
    }

    @GetMapping("/findPatientByID")
    public String getByID(ModelMap model){
        Integer id = 0;
        model.put("id",id);
        return "patient/findingPatientID";
    }

    @PostMapping("/findPatientByID")
    public String postID(Integer id){
        return "redirect:/patient/" + id ;
    }

    @GetMapping("/findPatientByName")
    public String getName(ModelMap model){
        String name="";
        model.put("name", name);
        return "patient/findingPatientByName";
    }

    @PostMapping("findPatientByName")
    public String postName(String name, ModelMap model){
        List<Patient> patients;
        try {
            patients = patientRepository.findByName(name);
        } catch (SQLException e) {
            return "system/error";
        }
        model.put("patients",patients);
        model.put("title","Querying By Name");
        return "patient/listResults";
    }

    @GetMapping("/petient/{patientID}")
    public String getPatientPage(@PathVariable("patientID") Integer patientID, ModelMap model) {
        Patient patient;
        try {
            patient = patientRepository.findByID(patientID);
        } catch (SQLException e) {
            return "system/error";
        }
        model.put("patient",patient);
        return "patient/patient";
    }


}
