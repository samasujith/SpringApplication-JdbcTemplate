package com.sujith.project.service_test_files;

import com.sujith.project.dto.*;
import com.sujith.project.entity.*;
import com.sujith.project.mapper.*;

import java.util.*;

public class EmployeeFiles {
    public static Employee getSingleEmployee() {
        return Employee.builder().firstName("sujith").id(1).lastName("reddy").salary(25000).experience(4).department("IT").build();
    }


    public static List<Employee> getListOfEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("sujith").id(1).lastName("reddy").salary(25000).experience(4).department("IT").build());
        return employeeList;
    }

    public static EmployeeDto getSingleEmployeeDto() {
        return EmployeeDto.builder().firstName("sujith").lastName("reddy").department("IT").experience(4).id(1).salary(25000).build();
    }

    public static Employee getSingleEmployeeWithCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().courseName("html").build());
        courseList.add(Course.builder().courseName("java").id(2).build());
        courseList.add(Course.builder().courseName("php").id(3).build());
        Address address = new Address(1, "ksp", "pvc", 507115);
        return new Employee(1, "sujith", "reddy", 25000, "IT", 3, address, courseList);
    }

    public static EmployeeDto getSingleEmployeeWithCoursesDto() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().courseName("html").build());
        courseList.add(Course.builder().courseName("java").id(2).build());
        courseList.add(Course.builder().courseName("php").id(3).build());
        Address address = new Address(1, "ksp", "pvc", 507115);
        return new EmployeeDto(1, "sujith", "reddy", 25000, "IT", 3, courseList, address);
    }

    public static Employee getUpdateSingleEmployeeWithCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().courseName("C++").id(4).build());
        courseList.add(Course.builder().courseName("java").id(2).build());
        courseList.add(Course.builder().courseName("php").id(3).build());
        Address address = new Address(1, "ksp", "pvc", 507115);
        return new Employee(1, "sama sujith", "reddy", 25000, "IT", 3, address, courseList);
    }


    public static List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(Course.builder().courseName("C").id(1).build());
        courses.add(Course.builder().courseName("java").id(2).build());
        courses.add(Course.builder().courseName("php").id(3).build());
        courses.add(Course.builder().courseName("C++").id(4).build());
        return courses;
    }

    public static EmployeeDto getEmployeeDtoWithCourse() {
        Employee emp = getSingleEmployeeWithCourses();
        EmployeeMapper employeeMapper = new EmployeeMapper();
        return employeeMapper.copyToDao(emp);
    }

    public static List<Employee> getIllegalStateEmployee() {
        List<Employee> emp = new ArrayList<>();
        Employee employee = new Employee();
        employee.setLastName("sujith");
        emp.add(employee);
        emp.add(getSingleEmployee());
        return emp;
    }

    public static List<Employee> getIllegalStateEmployeeResult() {
        List<Employee> emp = new ArrayList<>();
        emp.add(getSingleEmployee());
        return emp;
    }

    public static EmployeeDto getEmployeeDtoWithNoFirstName() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setLastName("reddy");
        return employeeDto;
    }

    public static Employee getEmployeeWithNoFirstName() {
        Employee employee = new Employee();
        employee.setLastName("reddy");
        return employee;
    }
}
