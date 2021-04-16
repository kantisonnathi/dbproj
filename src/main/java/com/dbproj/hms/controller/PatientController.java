package com.dbproj.hms.controller;

import com.dbproj.hms.model.Patient;
import com.dbproj.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.sql.SQLException;
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    PatientRepository patientRepository;

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
        return "patient/resultList";
    }

    @GetMapping("/patient/{patientID}")
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

    @GetMapping("/patient/new")
    public String newPatient(ModelMap modelMap) {
        Patient patient = new Patient();
        modelMap.put("patient",patient);
        return "patient/new";
    }

    @PostMapping("/patient/new")
    public String postNewPatient(Patient patient) {
        try {
            patient = patientRepository.save(patient);
        } catch (Exception e) {
            return "system/error";
        }
        return "redirect:/patient/" + patient.getPatientID();
    }

    @GetMapping("/patient/{patientId}/delete")
    public String deletePatient(@PathVariable("patientId") Integer patientId) {
        try {
            Patient patient = patientRepository.findByID(patientId);
            patientRepository.delete(patient);
        } catch (Exception e) {
            return "system/error";
        }
        return "redirect://";
    }

    @GetMapping("/patient/{patientId}/update")
    public String getUpdatePatient(@PathVariable("patientId") Integer patientID, ModelMap modelMap) {
        Patient patient;
        try {
            patient = patientRepository.findByID(patientID);
        } catch (Exception e) {
            return "system/error";
        }
        modelMap.put("patient",patient);
        return "patient/update";
    }

    @PostMapping("/patient/{patientId}/update")
    public String postUpdatePatient(Patient patient, ModelMap modelMap) {
        try {
            patientRepository.update(patient);
        } catch (Exception e) {
            return "system/error";
        }
        return "redirect:/patient/" + patient.getPatientID();
    }

    @GetMapping("/patient/all")
    public String getAllPatients(ModelMap modelMap) {
        List<Patient> patients;
        try {
            patients = this.patientRepository.findAll();
        } catch (Exception e) {
            modelMap.put("title","Sorry something went wrong");
            return "system/customError";
        }
        modelMap.put("patients",patients);
        modelMap.put("title","All Patients");
        return "patient/resultList";
    }

}
