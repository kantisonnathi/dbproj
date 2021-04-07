package com.dbproj.hms.controller;


import com.dbproj.hms.SpringSecurity.SecurityController;
import com.dbproj.hms.model.*;
import com.dbproj.hms.repository.AppointmentRepository;
import com.dbproj.hms.repository.DoctorRepository;
import com.dbproj.hms.repository.EmployeeRepository;
import com.dbproj.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
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
    SecurityController securityController=new SecurityController();

    @GetMapping("/doc/{docid}/bookAppointment")
    public String getBookAppointment(@PathVariable("docid") Integer docid, ModelMap modelMap) {
        Doctor doctor;
        Patient patient = new Patient();
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
        modelMap.put("patient",patient);
        modelMap.put("doctor",doctor);
        modelMap.put("appointment",appointment);
        return "appointment/new";
    }

    @PostMapping("/doc/{docid}/bookAppointment")
    public String postBookAppointment(Appointment appointment, Patient patient, ModelMap modelMap) {
        //first check if patient is valid
        Boolean valid;
        try {
            patient = this.patientRepository.findByPhnoAndName(patient.getPhno(), patient.getPatientName());
            Doctor doctor = this.doctorRepository.findByID(appointment.getDocID());
            valid = this.employeeRepository.isEmpOnLeave(doctor.getEmpID(),appointment.getDate());
        } catch (SQLException throwables) {
            //no patient. for now just error yourself
            return "system/error";
        }
        if (valid) {
            //welp. error yourself - emp on leave. create page for that
            return "system/error";
        }
        //valid patient. woohoo
        try {
            appointment.setPatientID(patient.getPatientID());
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

    @GetMapping("/appointment/{appointmentID}/delete")
    public String deleteAppointment(@PathVariable("appointmentID") Integer appointmentID, ModelMap modelMap) {
        Appointment appointment;
        try {
            appointment = this.appointmentRepository.findById(appointmentID);
            this.appointmentRepository.delete(appointment);
        } catch (Exception e) {
            return "system/error";
        }
        return "main";
    }

    @GetMapping("/doc/{docid}/listAppointments")
    public String getAppointmentList(@PathVariable("docid") Integer docid, ModelMap modelMap){
        List<Appointment> appointmentList;
        //Patient patient;
        //Doctor doctor;
        try {
            appointmentList = this.appointmentRepository.findByDocId(docid);
        } catch (Exception e){
            return "system/error";
        }
        modelMap.put("appointments", appointmentList);
        return "appointment/listResult";
    }

    @GetMapping("patient/{patientid}/listAppointments")
    public String getAppointmentListbyPatient(@PathVariable("patientid") Integer patientid, ModelMap modelMap){
        List<Appointment> appointmentList;
        try{
            appointmentList = this.appointmentRepository.findByPatientId(patientid);
        } catch (Exception e){
            return "system/error";
        }
        modelMap.put("appointments", appointmentList);
        return "appointment/listResult";
    }

    @GetMapping("/transaction")
    public String getpatientdetails(ModelMap model) {
        Patient patient=new Patient();
        model.put("patient",patient);
        return "appointment/transaction";
    }

    @PostMapping("/transaction")
    public String postfinalcost(Patient patient,ModelMap model) throws SQLException {

        try {
            patient= patientRepository.findByPhnoAndName(patient.getPhno(),patient.getPatientName());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        model.put("patient",patient);
        return "appointment/display";
    }

    @GetMapping("/transaction/{patientid}")
    public String displaydetails(@PathVariable("patientid") Integer patientid,ModelMap model) {
        List<Transaction> transactions;
        try {
            transactions = appointmentRepository.getprice(patientid);
        }
        catch (Exception e)
        {
            return "system/error";
        }
        int sum=0;
        for(int i=0;i<transactions.size();i++)
        {
            sum+=transactions.get(i).getVisitationFees();
        }
        Transaction transaction=new Transaction();
        transaction.setTotalcost(sum);
        transaction.setPatientid(patientid);
        appointmentRepository.addtransaction(transaction);
        model.put("sum",sum);
        model.put("transaction",transaction);
        return "appointment/costresult";
    }

    @PostMapping("/transaction/{patientid}")
    public String clearbalance(@PathVariable("patientid") Integer patientid,ModelMap model)  {
        List<Appointment> appointments;
        appointmentRepository.updateappointment(patientid);
        return "appointment/paymentsuccess";
    }

    @GetMapping("/appointment/{appointmentID}/update")
    public String getUpdateAppointment(@PathVariable("appointmentID") Integer appointmentID, ModelMap modelMap) {
        Appointment appointment;
        Patient patient;
        try {
            appointment = appointmentRepository.findById(appointmentID);
            patient = this.patientRepository.findByID(appointment.getPatientID());
        } catch (Exception e) {
            return "system/error";
        }
        modelMap.put("appointment",appointment);
        modelMap.put("patient",patient);
        return "appointment/update";
    }

    @PostMapping("/appointment/{appointmentID}/update")
    public String postUpdateAppointment(Appointment appointment ,ModelMap modelMap) {
        try {
            appointmentRepository.update(appointment);
        } catch (Exception e) {
            return "system/error";
        }
        return "redirect:/appointment/"+appointment.getID();
    }

}
