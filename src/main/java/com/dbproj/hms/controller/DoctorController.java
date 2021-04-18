package com.dbproj.hms.controller;


import com.dbproj.hms.model.*;
import com.dbproj.hms.repository.AppointmentRepository;
import com.dbproj.hms.repository.DoctorRepository;
import com.dbproj.hms.repository.EmployeeRepository;
import com.dbproj.hms.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.dbproj.hms.model.Slot.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@CrossOrigin
public class DoctorController {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    NurseRepository nurseRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/findDoctorByID")
    public String getByID(ModelMap model) {
        //this is the get method;
        //post, we get the doctor and show page ez
        Integer id = 0;
        model.put("id",id);
        return "doctor/findingDoctorID";
    }

    @PostMapping("/findDoctorByID")
    public String postID(Integer id, ModelMap modelMap) {
        //System.out.println("found doctor:" + doctor.toString());
        try {
            Doctor doctor = this.doctorRepository.findByID(id);
            if (doctor == null) {
                modelMap.put("title","There is no such doctor");
                return "system/customError";
            }
        } catch (Exception e) {
            modelMap.put("title","Sorry something went wrong :(");
        }
        return "redirect:/doc/" + id ;
    }

    @GetMapping("/findDoctorByName")
    public String getName(ModelMap model) {
        String name = "";
        model.put("name",name);
        return "doctor/findingDocByName";
    }

    @PostMapping("/findDoctorByName")
    public String postName(String name, ModelMap model) {
        List<Doctor> doctors;
        try {
            doctors = doctorRepository.findByName(name);
            //System.out.println("Hello");
        } catch (SQLException e) {
            return "system/error";
        }
        if (doctors.isEmpty()) {
            model.put("title","There are no doctors with this name");
            return "system/customError";
        }
        model.put("doctors",doctors);
        model.put("title","Querying By Name");
        return "doctor/listResults";
    }

    @GetMapping("/findDoctorBySpeciality")
    public String getSpeciality(ModelMap modelMap) {
        String speciality = "";
        modelMap.put("speciality",speciality);
        return "doctor/findingDocBySpeciality";
    }

    @PostMapping("/findDoctorBySpeciality")
    public String postSpeciality(String speciality, ModelMap modelMap) {
        List<Doctor> doctors;
        try {
            doctors = doctorRepository.findBySpeciality(speciality);
        } catch (SQLException e) {
            return "system/error";
        }
        if (doctors.isEmpty()) {
            modelMap.put("title","There are no doctors with this speciality");
            return "system/customError";
        }
        modelMap.put("doctors",doctors);
        modelMap.put("title","Querying By Speciality");
        return "doctor/listResults";
    }

    @GetMapping("/doc/{docID}")
    public String getDocPage(@PathVariable("docID") Integer DocID, ModelMap model) {
        Doctor doctor;
        Employee employee;
        try {
            doctor= doctorRepository.findByID(DocID);
            employee = employeeRepository.findByID(doctor.getEmpID());
        } catch (SQLException e) {
            return "system/error";
        }
        model.put("doctor",doctor);
        model.put("employee", employee);
        return "doctor/doctor";
    }


    @GetMapping("/adddoctor")
    public String getformpage(ModelMap modelMap){
        Doctor doctor= new Doctor();

        modelMap.put("doctor",doctor);
        return "doctor/newdoctor";
    }


    @PostMapping("/adddoctor")
    public String postaddition(Doctor doctor,ModelMap modelMap) {
        try {
            doctor.setAuthorization("ROLE_USER");
            doctor.setVerify(1);
           doctor.setPassword(BCrypt.hashpw(doctor.getPassword(), BCrypt.gensalt()));
            doctor = doctorRepository.save(doctor);
        }
        catch (SQLException e) {
            return "system/error";
        }
        return "redirect:/doc/" + doctor.getID();
    }

    @GetMapping("/doc/all")
    public String viewAllDoctors(ModelMap modelMap) {
        try {
            List<Doctor> list = this.doctorRepository.listAllDoctors();
            modelMap.put("doctors", list);
            modelMap.put("title", "All Doctors");
        } catch (Exception e) {
            modelMap.put("title","Sorry somethign went wrong");
            return "system/customError";
        }
        return "doctor/listResults";
    }

    @RequestMapping("/doc/{docid}/delete")
    @Transactional
    public String deletedoc(@PathVariable("docid") int docid, ModelMap modelMap) throws SQLException {
        try {
            Doctor doctor=doctorRepository.findByID(docid);
            List<Appointment> list = this.appointmentRepository.findByDocId(docid);
            if (!list.isEmpty()) {
                modelMap.put("doctor",doctor);
                String message = "There are appointments associated with this doctor. Please delete or reassign these" +
                        " appointments before deleting this doctor";
                modelMap.put("title",message);
                return "system/intermediate";
            }
            doctorRepository.delete(doctor);
        }
        catch(SQLException e) {
            return "system/error";
        }
       return "main";
    }

    @GetMapping("/doc/{docid}/update")
    public String getUpdatingNMP(@PathVariable("docid") Integer docid, ModelMap modelMap) {
        Doctor doctor;
        Employee emp;
        try {
            doctor = doctorRepository.findByID(docid);
            emp = employeeRepository.findByID(doctor.getEmpID());
        } catch (Exception e) {
            return "system/error";
        }
        modelMap.put("doctor", doctor);
        modelMap.put("emp",emp);
        modelMap.put("empID",emp.getID());
        return "doctor/update";
    }

    @PostMapping("/doc/{docid}/update")
    public String postUpdatingNMP(Doctor doctor, Employee emp, Integer empID,ModelMap modelMap) {
        try {
            doctor.setAuthorization();
            doctor.setVerify(1);
            doctor.setEmpID(empID);
            doctor = doctorRepository.update(doctor);
            /*emp.setAuthorization("ROLE_USER");
            emp.setVerify(1);*/

            //emp = employeeRepository.update(nmp);

        } catch (Exception e) {
            return "system/error";
        }
        return "redirect:/doc/"+doctor.getID();
    }

    @GetMapping("/doc/{docid}/addNurse")
    public String addNurseToDoc(ModelMap modelMap) {
        Integer id = null;
        modelMap.put("id",id);
        return "doctor/addNurse";
    }

    @PostMapping("/doc/{docid}/addNurse")
    public String postAddingNurse(@PathVariable("docid") Integer docid, Integer id) {
        try {
            this.doctorRepository.addNurseToDoctor(docid, id);
        } catch (Exception e) {
            return "system/error";
        }
        return "redirect:/doc/" + docid + "/nurses";
    }

    @GetMapping("/doc/{docid}/timings")
    public String getBookappontment(@PathVariable("docid") Integer docid, ModelMap model){
        Doctor doctor;
        Slot slot;
        List<Slot> slots;
        try{
            doctor=doctorRepository.findByID(docid);
            slots=doctorRepository.getslots(doctor);
        }
        catch (Exception e)
        {
            return "system/error";
        }
        model.put("slots",slots);
        return "doctor/showtimings";
    }

    @GetMapping("/doc/{docid}/nurses")
    public String getNursesWorkingUnder(@PathVariable("docid") Integer DocID, ModelMap model){
        List<Nurse> list;
        try{
            list = this.nurseRepository.findNursesWorkingUnder(DocID);

        }catch (Exception e){
            return "system/error";
        }
        model.put("nurses", list);

        return "nurse/listResults";
    }

}
