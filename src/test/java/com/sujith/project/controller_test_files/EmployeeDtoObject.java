package com.sujith.project.controller_test_files;

import com.sujith.project.dto.*;
import com.sujith.project.entity.*;
import com.sujith.project.mapper.*;

import java.util.*;

public class EmployeeDtoObject {
    static EmployeeMapper employeeMapper;

    public static EmployeeDto getStr() {
        return EmployeeDto.builder().id(1).firstName("sujith").salary(45000).lastName("reddy").department("It").experience(3).build();
    }

    public static EmployeeDto getInvalidStr() {
        return new EmployeeDto();
    }

    public static String getString() {
        return EmployeeDto.builder().id(1).firstName("sujith").salary(45000).lastName("reddy").department("It").experience(3).build().toString();
    }

    public static List<Employee> getEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        employees.add(Employee.builder().id(1).firstName("sujith").salary(45000).lastName("reddy").department("It").experience(3).build());
        return employees;
    }

    public static Employee getEmp() {
        return Employee.builder().id(1).firstName("sujith").salary(45000).lastName("reddy").department("It").experience(3).build();
    }

    public static String getEmpJson() {
        return """
                                
                    {
                       \s
                        "firstName": "sujith",
                        "lastName": "reddy",
                        "salary": 45000,
                        "department": "IT",
                        "experience": 3,
                        "address": null,
                        "courseList": null
                    }
                                
                """;
    }

    public static String getInvalidEmpJson() {
        return """
                                
                    {
                     
                    }
                                
                """;
    }

    public static String getEmpListJson() {
        return """
                   [             
                    {
                       \s
                        "firstName": "sujith",
                        "lastName": "reddy",
                        "salary": 45000,
                        "department": "IT",
                        "experience": 3,
                        "address": null,
                        "courseList": null
                    }
                     ]           
                """;
    }
}
