package com.sujith.project.controller_test_files;

import com.sujith.project.entity.*;

import java.util.*;

public class CourseDtoObject {
    public static List<Course> getCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().courseName("C").id(1).build());
        courseList.add(Course.builder().courseName("C++").id(2).build());
        return courseList;
    }

    public static List<Course> getInvalidCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course());
        courseList.add(new Course());
        return courseList;
    }

    public static Course getCourse() {
        return Course.builder().courseName("C").id(1).build();
    }

    public static List<Course> getInvalidCourseList() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder().courseName("").build());
        courseList.add(Course.builder().courseName("").build());
        return courseList;
    }


    public static Course getInvalidCourse() {
        return new Course();
    }

    public static String getJson() {
        return """
                {
                "id":1,
                    "courseName": "MYSQL"
                }
                """;
    }

    public static String getJsonList() {
        return """
                [
                    {
                     "courseName":"C"
                    },
                    {
                      "courseName":"C"
                    }
                ]
                """;
    }

    public static String getInvalidJson() {
        return """
                {
                                
                }
                """;
    }

    public static String getInvalidJsonList() {
        return """
                [
                    {
                     "courseName":""
                    },
                    {
                      "courseName":""
                    }
                ]
                """;
    }

}
