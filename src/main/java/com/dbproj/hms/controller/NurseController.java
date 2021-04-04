package com.dbproj.hms.controller;
import com.dbproj.hms.model.Nurse;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.repository.NurseRepository;
import com.dbproj.hms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    EmployeeRepository employeeRepository;
    @GetMapping("/findNurseByID")
    public String getByID(ModelMap model) {
        Integer id = 0;
        model.put("id",id);
        return "nurse/findingNurseByID";
    }

    @PostMapping("/findNurseByID")
    public String postID(Integer id) {
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
            nurse.setVerify(1);
            nurseRepository.save(nurse);
        }
        catch (SQLException e) {
            return "system/error";
        }
        return "main";
    }

    @RequestMapping("/nur/{nurid}/delete")
    @Transactional
    public String deletenur(@PathVariable("nurid") int nurid) throws SQLException {
        try {
            Nurse nurse=nurseRepository.findByID(nurid);
            nurseRepository.delete(nurse);
        }
        catch(SQLException e) {
            return "system/error";
        }
        return "main";
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
        return "redirect:/nur/"+nurse.getID();
    }

}

