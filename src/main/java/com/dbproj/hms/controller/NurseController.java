package com.dbproj.hms.controller;
import com.dbproj.hms.model.Nurse;
import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.repository.NurseRepository;
import com.dbproj.hms.repository.DoctorRepository;
import com.dbproj.hms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@Controller
@CrossOrigin
public class NurseController {

    @Autowired
    NurseRepository nurseRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/findNurseByID")
    public String getByID(ModelMap model) {
        Integer id = 0;
        model.put("id",id);
        return "nurse/findingNurseByID";
    }

    @PostMapping("/findNurseByID")
    public String postID(Integer id, ModelMap modelMap) {
        try {
            Nurse nurse = this.nurseRepository.findByID(id);
            if (nurse == null) {
                modelMap.put("title", "There is no such nurse");
                return "system/customError";
            }
        } catch (Exception e) {
            modelMap.put("title","Sorry something went wrong :(");
            return "system/customError";
        }
        return "redirect:/nur/" + id ;
    }

    @GetMapping("/findNurseByName")
    public String getName(ModelMap model) {
        String name = "";
        model.put("name",name);
        return "nurse/findingNurseByName";
    }

    @PostMapping("/findNurseByName")
    public String postName(String name, ModelMap model) {
        List<Nurse> nurses;
        try {
            nurses = nurseRepository.findByName(name);
        } catch (SQLException e) {
            return "system/error";
        }
        if (nurses.isEmpty()) {
            model.put("title","There are no nurses with this name");
            return "system/customError";
        }
        model.put("nurses",nurses);
        model.put("title","Querying By Name");
        return "nurse/listResults";
    }

    @GetMapping("/nur/{nurID}")
    public String getNurPage(@PathVariable("nurID") Integer NurID, ModelMap model) {
        Nurse nurse;
        Employee employee;
        try {
            nurse = nurseRepository.findByID(NurID);
            employee = employeeRepository.findByID(nurse.getEmpID());
        } catch (SQLException e) {
            return "system/error";
        }
        model.put("nurse",nurse);
        model.put("employee", employee);
        return "nurse/nurse";
    }

    @GetMapping("/addnurse")
    public String getformpage(ModelMap modelMap){
        Nurse nurse= new Nurse();

        modelMap.put("nurse",nurse);
        return "nurse/newNurse";
    }

    @PostMapping("/addnurse")
    public String postaddition( Nurse nurse,ModelMap modelMap) {
        try {
            nurse.setAuthorization();
            nurse.setPassword(BCrypt.hashpw(nurse.getPassword(), BCrypt.gensalt()));
            nurse.setVerify(1);
            nurseRepository.save(nurse);
        }
        catch (SQLException e) {
            return "system/error";
        }
        return "redirect:";
    }

    @GetMapping("/nur/all")
    public String getAll(ModelMap modelMap) {
        List<Nurse> list = this.nurseRepository.listAllNurses();
        modelMap.put("nurses",list);
        modelMap.put("title","All Nurses");
        return "nurse/listResults";
    }

    @RequestMapping("/nur/{nurid}/delete")
    @Transactional
    public String deletenur(@PathVariable("nurid") int nurid, ModelMap modelMap) throws SQLException {
        try {
            Nurse nurse=nurseRepository.findByID(nurid);
            List<Doctor> list = this.doctorRepository.findDocWorkingWith(nurid);
            if (!list.isEmpty()) {
                modelMap.put("title","There are doctors working with this nurse, this nurse cannot be deleted");
                return "system/customError";
            }
            nurseRepository.delete(nurse);
        }
        catch(SQLException e) {
            return "system/error";
        }
        return "redirect:/";
    }

    @GetMapping("/nur/{nurid}/update")
    public String getUpdatingNMP(@PathVariable("nurid") Integer nurid, ModelMap modelMap) {
        Nurse nurse;
        Employee emp;
        try {
            nurse = nurseRepository.findByID(nurid);
            emp = employeeRepository.findByID(nurse.getEmpID());
        } catch (Exception e) {
            return "system/error";
        }
        modelMap.put("nurse",nurse);
        modelMap.put("emp",emp);
        modelMap.put("empID",emp.getID());
        return "nurse/updateNurse";
    }

    @PostMapping("/nur/{nurid}/update")
    public String postUpdatingNMP(Nurse nurse, Employee emp, Integer empID,ModelMap modelMap) {
        try {
            nurse.setAuthorization();
            nurse.setVerify(1);
            nurse.setEmpID(empID);
            nurse = nurseRepository.update(nurse);

             } catch (Exception e) {
            return "system/error";
        }
        return "redirect:/nur/"+nurse.getNurseID();
    }

    @GetMapping("/nur/{nurid}/doctors")
    public String getDocWorkingWith(@PathVariable("nurid") Integer nurid, ModelMap model) {
        List<Doctor> list;
        try{
            list = this.doctorRepository.findDocWorkingWith(nurid);
        }
        catch(Exception e){
            return "system/error";
        }
        model.put("doctors", list);

        return "doctor/listResults";
    }

}

