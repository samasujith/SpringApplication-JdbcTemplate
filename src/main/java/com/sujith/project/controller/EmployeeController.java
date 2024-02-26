package com.sujith.project.controller;

import com.sujith.project.dto.*;
import com.sujith.project.entity.*;
import com.sujith.project.jpaservice.*;
import com.sujith.project.mapper.FormDataMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class EmployeeController {
    EmployeeJpaService employeeService;

    @Autowired
    public EmployeeController(EmployeeJpaService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/findAll")
    public List<Employee> findAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        return employeeService.findAll(page,size);
    }

    @GetMapping("/employee/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public EmployeeDto getById(@PathVariable int id) {
        return employeeService.findById(id);
    }

    @GetMapping("/employee")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public List<Employee> getByName(@RequestParam String firstName) {
        return employeeService.getByName(firstName);
    }

    @GetMapping("/employee/department")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public List<Employee> getByDepartment(@RequestParam String deptName) {
        return employeeService.findByDepartment(deptName);

    }

    @GetMapping("/employee/maxSalary")
    public int maxSalary() {
        return employeeService.maxSalary();
    }

    @GetMapping("/employee/department/maxSalary")
    public int getEmpMax(@RequestParam String departmentName) {
        return employeeService.maxInDept(departmentName);
    }

    @GetMapping("/employee/course")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public List<Employee> employeeWithCourseName(@RequestParam String name) {
        return employeeService.getEmployeesByCourseName(name);
    }

    @PostMapping(path="/employee",  consumes = "application/x-www-form-urlencoded")
    public Employee insertEmployeeWithJson(@ModelAttribute FormDataMapper theEmployee) {

        return employeeService.save(theEmployee.getEmployeeDto());
    }
    @PostMapping("/employee")
    public Employee insertEmployee(@RequestBody EmployeeDto theEmployee) {

        return employeeService.save(theEmployee);
    }

    @PostMapping("/employee/insertAll")
    public List<Employee> insertAll(@RequestBody List<Employee> theEmployee) {
        return employeeService.saveAll(theEmployee);
    }

    @DeleteMapping("/employee/{id}")
    public void deleteById(@PathVariable int id) {
        employeeService.delete(id);
    }


    @PutMapping("/employee")

    public Employee updateEmployee(@RequestBody EmployeeDto theEmployee) {

        return employeeService.update(theEmployee);

    }

    @PutMapping("/employee/updateSalaryById")
    public Employee updateSalaryById(@RequestParam int id, @RequestParam int salary) {
        return employeeService.updateSalaryById(id, salary);
    }


}
