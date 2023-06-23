package com.learn.springboot.cruddemo.rest;

import com.learn.springboot.cruddemo.dao.EmployeeDAO;
import com.learn.springboot.cruddemo.entity.Employee;
import com.learn.springboot.cruddemo.service.EmployeeService;
import com.learn.springboot.cruddemo.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;
    //quick and dirty: inject employee dao (use constructor injection)

    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService){
        employeeService = theEmployeeService;
    }

    //expose "/employee" and return a list of employees

    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployeeId(@PathVariable("employeeId") int employeeId){
        Employee theEmployee = employeeService.findById(employeeId);

        if (theEmployee == null){
            throw  new RuntimeException("Employee id not found - "+ employeeId);
        }
        return theEmployee;
    }

    // add mapping for POST /employee - add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){
        // also just in case they pass an id in JSON ... set id to 0

        // this is to force a save of new item ... instead of update
        theEmployee.setId(0);
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        Employee dbEmployee =  employeeService.save(theEmployee);
        return  dbEmployee;
    }

    @DeleteMapping("/employees/{empId}")
    public  String deleteEmployee(@PathVariable("empId") int empId){
        Employee tempEmployee = employeeService.findById(empId);

        //throw exception if null
        if (tempEmployee == null){
            throw new RuntimeException("Employee id not found -");
        }

        employeeService.deleteById(empId);

        return "Deleted employee id - " + empId;
    }

}
