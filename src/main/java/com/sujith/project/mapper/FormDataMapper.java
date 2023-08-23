package com.sujith.project.mapper;

import com.sujith.project.dto.EmployeeDto;
import com.sujith.project.entity.Address;
import com.sujith.project.entity.Course;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class FormDataMapper {
    private String firstName;

    private String lastName;

    private int salary;

    private String department;

    private int experience;
    private String street;
    private int pin;
    private String city;
    private String course;
    Address getAddress(){
        Address address=new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setPin(pin);
        return address;
    }
    List<Course> getCourse(){
        Course course1=new Course();
        course1.setCourseName(course);
        List<Course> courseList=new ArrayList<>();
        courseList.add(course1);
        return courseList;
    }
    List<Course> getCourseList(){
        String [] courses=course.split(",");
        List<Course> courseList=new ArrayList<>();
       for(String course:courses){
           Course course1=new Course();
           course1.setCourseName(course);
           courseList.add(course1);
       }
        return courseList;
    }
    public EmployeeDto getEmployeeDto(){
return EmployeeDto.builder().firstName(firstName).lastName(lastName).salary(salary).department(department).experience(experience).address(getAddress()).courseList(getCourseList()).build()   ;
    }

}
