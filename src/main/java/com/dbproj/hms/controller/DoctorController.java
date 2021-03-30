package com.dbproj.hms.controller;


import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.repository.DoctorRepository;
import com.dbproj.hms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.sql.SQLException;
import java.util.List;

@Controller
@CrossOrigin
public class DoctorController {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    /*public DoctorController() throws SQLException, ClassNotFoundException {
        doctorRepository = new DoctorRepository();
        employeeRepository = new EmployeeRepository();
    }
*/
    @GetMapping("/findDoctorByID")
    public String getByID(ModelMap model) {
        //this is the get method;
        //post, we get the doctor and show page ez
        Integer id = 0;
        model.put("id",id);
        return "doctor/findingDoctorID";
    }

    @PostMapping("/findDoctorByID")
    public String postID(Integer id) {
        //System.out.println("found doctor:" + doctor.toString());
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
            doctor.setAuthorization();
            doctor.setVerify(1);
            doctorRepository.save(doctor);
        }
        catch (SQLException e) {
            return "system/error";
        }
        return "main";
    }

    @RequestMapping("/doc/{docid}/delete")
    @Transactional
    public String deletedoc(@PathVariable("docid") int docid) throws SQLException {
        try {
            Doctor doctor=doctorRepository.findByID(docid);
            doctorRepository.delete(doctor);
        }
        catch(SQLException e) {
            return "system/error";
        }
       return "main";
    }

    @GetMapping("/doc/{docid}/update")
    @Transactional
    public String updatedoc(@PathVariable("docid")int docid,ModelMap model )throws SQLException{
        try {
            Doctor doctor=doctorRepository.findByID(docid);
            model.put("doctor",doctor);
        }
        catch (SQLException e) {
            return "system/error";
        }
        return "doctor/update";
    }


    @PostMapping("/doc/{docid/update")
    public String postupdatedoc(Doctor doctor,ModelMap model) throws SQLException {
       try {
           doctorRepository.save(doctor);
           model.put("doctor",doctor);
       }
       catch (SQLException e)
       {
           return "system/error";
       }
        return "main";
    }
}
