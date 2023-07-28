package com.sujith.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.slf4j.*;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    private static Logger logger = LoggerFactory.getLogger(Employee.class);
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @NonNull
    private String firstName;
    @Column(name = "last_name")

    private String lastName;
    @Column(name = "salary")
    private int salary;
    @Column(name = "department")
    private String department;
    @Column(name = "experience")
    private int experience;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.ALL})
    @JoinColumn(name = "address_id")
    private Address address;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "employee_course", joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    // @JsonManagedReference
    //    @JsonIdentityInfo(
    //            generator = ObjectIdGenerators.PropertyGenerator.class,
    //            property = "id")
    // @NonNull
    private List<Course> courseList;

}
