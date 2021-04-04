package com.dbproj.hms.controller;


import com.dbproj.hms.model.Appointment;
import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Patient;
import com.dbproj.hms.model.Slot;
import com.dbproj.hms.repository.AppointmentRepository;
import com.dbproj.hms.repository.DoctorRepository;
import com.dbproj.hms.repository.EmployeeRepository;
import com.dbproj.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
@CrossOrigin
public class AppointmentController {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    AppointmentRepository appointmentRepository;


    @GetMapping("/doc/{docid}/bookAppointment")
    public String getBookAppointment(@PathVariable("docid") Integer docid, ModelMap modelMap) {
        Doctor doctor;
        List<Slot> slots;
        try {
            doctor=doctorRepository.findByID(docid);
            slots=doctorRepository.getslots(doctor);
        }
        catch (Exception e) {
            return "system/error";
        }
        Appointment appointment = new Appointment();
        appointment.setDocID(doctor.getID());
        modelMap.put("slots",slots);
        modelMap.put("doctor",doctor);
        modelMap.put("appointment",appointment);
        return "appointment/new";
    }

    @PostMapping("/doc/{docid}/bookAppointment")
    public String postBookAppointment(Appointment appointment, ModelMap modelMap) {
        //first check if patient is valid
        try {
            this.patientRepository.findByID(appointment.getPatientID());
        } catch (SQLException throwables) {
            //no patient. for now just error yourself
            return "system/error";
        }
        //valid patient. woohoo
        try {
            appointment = this.appointmentRepository.save(appointment);
        } catch (Exception e) {
            return "system/error";
        }
        modelMap.put("appointment", appointment);
        return "redirect:/appointment/" + appointment.getID();

    }

    @GetMapping("/appointment/{appointmentId}")
    public String getAppointmentDetails(@PathVariable("appointmentId") Integer appointmentId, ModelMap modelMap) {
        Appointment appointment;
        Patient patient;
        Doctor doctor;
        try {
            appointment = this.appointmentRepository.findById(appointmentId);
            patient = this.patientRepository.findByID(appointment.getPatientID());
            doctor = this.doctorRepository.findByID(appointment.getDocID());
        } catch (Exception e) {
            return "system/error";
        }
        modelMap.put("appointment",appointment);
        modelMap.put("doctor",doctor);
        modelMap.put("patient", patient);
        return "appointment/appointment";

    }

    @GetMapping("/findDoctorByID")
    public String getByID(ModelMap model) {
        //this is the get method;
        //post, we get the doctor and show page ez
        Integer id = 0;
        model.put("id",id);
        return "appointment/findDocByID";
    }





}
