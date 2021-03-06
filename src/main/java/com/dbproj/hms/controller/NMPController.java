package com.dbproj.hms.controller;

import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;
import com.dbproj.hms.model.NMP;
import com.dbproj.hms.model.Transaction;
import com.dbproj.hms.repository.EmployeeRepository;
import com.dbproj.hms.repository.NMPRepository;
import com.dbproj.hms.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
public class NMPController {

    @Autowired
    NMPRepository nmpRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    /*public NMPController() throws SQLException, ClassNotFoundException {
        nmpRepository = new NMPRepository();
        employeeRepository = new EmployeeRepository();
    }*/

    @GetMapping("/findNMPByID")
    public String getID(ModelMap modelMap) {
        Integer id = 0;
        modelMap.put("id",id);
        return "NMP/findingNMPByID";
    }

    @PostMapping("/findNMPByID")
    public String postID(Integer id, ModelMap modelMap) {
        try {
            NMP nmp = this.nmpRepository.findByID(id);
            if (nmp == null) {
                modelMap.put("title","There is no such non medical professional");
                return "system/customError";
            }
        } catch (Exception e) {
            modelMap.put("title","Sorry something went wrong :(");
            return "system/customError";
        }
        return "redirect:/nmp/"+ id;
    }


    @GetMapping("/findNMPByName")
    public String getName(ModelMap modelMap) {
        String name = "";
        modelMap.put("name",name);
        return "NMP/findingNMPByName";
    }

    @PostMapping("/findNMPByName")
    public String postName(String name, ModelMap modelMap) {
        List<NMP> list;
        try {
            list = nmpRepository.findByName(name);
        } catch (Exception e) {
            return "system/error";
        }
        if (list.isEmpty()) {
            modelMap.put("title","There are no non medical professionals with this name");
            return "system/customError";
        }
        modelMap.put("nmps",list);
        modelMap.put("title","Querying nmps by name");
        return "NMP/listResults";
    }

    @GetMapping("/findNMPByTitle")
    public String getTitle(ModelMap modelMap) {
        String title = "";
        modelMap.put("name", title);
        return "NMP/findingNMPByName";
    }

    @PostMapping("/findNMPByTitle")
    public String postTitle(String name, ModelMap modelMap) {
        List<NMP> list;
        try {
            list = nmpRepository.findByTitle(name);
        } catch (Exception e) {
            return "system/error";
        }
        if (list.isEmpty()) {
            modelMap.put("title","There are no non medical professionals with this title");
            return "system/customError";
        }
        modelMap.put("nmps",list);
        modelMap.put("title","Querying nmps by title");
        return "NMP/listResults";
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

    @GetMapping("/nmp/{nmpID}/delete")
    public String deleteNMP(@PathVariable("nmpID") Integer nmpID, ModelMap modelMap) {
        try {
            NMP nmp = nmpRepository.findByID(nmpID);
            List<Transaction> list = this.nmpRepository.gettransactions(nmpID);
            if (!list.isEmpty()) {
                modelMap.put("title","There are transactions associated with this non medical professional");
                return "system/customError";
            }
            nmpRepository.delete(nmp);
        } catch (Exception e) {
            return "system/error";
        }
        return "redirect:/";
    }


    @GetMapping("/nmp/new")
    public String newNMP(ModelMap modelMap) {
        NMP nmp = new NMP();
        modelMap.put("nmp",nmp);
        return "NMP/new";
    }

    @PostMapping("/nmp/new")
    public String savingNewNMP(NMP nmp, Employee employee, ModelMap modelMap) {
        try {
            nmp.setAuthorization("ROLE_ADMIN");
            nmp.setPassword(BCrypt.hashpw(nmp.getPassword(), BCrypt.gensalt()));
            nmp.setVerify(1);
            nmp = nmpRepository.save(nmp);
        } catch (Exception e) {
            return "system/error";
        }
        return "redirect:/nmp/"+nmp.getID();
    }

    @GetMapping("/nmp/{nmpID}/update")
    public String getUpdatingNMP(@PathVariable("nmpID") Integer nmpID, ModelMap modelMap) {
        NMP nmp;
        Employee emp;
        try {
            nmp = nmpRepository.findByID(nmpID);
            emp = employeeRepository.findByID(nmp.getEmpID());
        } catch (Exception e) {
            return "system/error";
        }
        modelMap.put("nmp", nmp);
        modelMap.put("emp",emp);
        modelMap.put("empID",emp.getID());
        return "NMP/update";
    }

    @PostMapping("/nmp/{nmpID}/update")
    public String postUpdatingNMP(NMP nmp, Employee emp, Integer empID,ModelMap modelMap) {
        try {
            nmp.setAuthorization();
            nmp.setVerify(1);
            nmp.setEmpID(empID);
            nmp = nmpRepository.update(nmp);
            /*emp.setAuthorization("ROLE_USER");
            emp.setVerify(1);*/

            //emp = employeeRepository.update(nmp);

        } catch (Exception e) {
            return "system/error";
        }
        return "redirect:/nmp/"+nmp.getID();
    }
    @GetMapping("/nmp/{nmpID}/transaction")
    public String pasttransactions(@PathVariable("nmpID") Integer nmpID, ModelMap modelMap) throws SQLException {
        List<Transaction> transactions;
        NMP n=new NMP();
        n=nmpRepository.findByID(nmpID);
        transactions=nmpRepository.gettransactions(n.getID());
        modelMap.put("transactions",transactions);
        return "NMP/transaction details";
    }

    @GetMapping("/nmp/all")
    public String getAll(ModelMap modelMap) {
        List<NMP> list = this.nmpRepository.listAllNMPs();
        modelMap.put("nmps",list);
        modelMap.put("title","All NMPs");
        return "NMP/listResults";
    }
}
