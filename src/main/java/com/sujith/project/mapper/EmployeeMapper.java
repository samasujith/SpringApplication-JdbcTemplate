package com.sujith.project.mapper;

import com.sujith.project.dto.*;
import com.sujith.project.entity.*;
import org.springframework.stereotype.*;

@Component
public class EmployeeMapper {


    public EmployeeDto copyToDao(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setExperience(employee.getExperience());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setDepartment(employee.getDepartment());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setCourseList(employee.getCourseList());
        return employeeDto;
    }

    public Employee copyToEmp(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setExperience(employeeDto.getExperience());
        employee.setSalary(employeeDto.getSalary());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setAddress(employeeDto.getAddress());
        employee.setCourseList(employeeDto.getCourseList());
        return employee;
    }

}
