package com.sujith.project.dto;

import com.sujith.project.entity.*;
import lombok.*;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private int id;
    @NonNull
    private String firstName;

    private String lastName;

    private int salary;

    private String department;

    private int experience;
    private List<Course> courseList;
    private Address address;
}
