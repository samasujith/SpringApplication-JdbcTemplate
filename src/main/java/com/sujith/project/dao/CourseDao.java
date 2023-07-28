package com.sujith.project.dao;

import com.sujith.project.entity.*;

import java.util.*;

public interface CourseDao {
    List<Course> getAll();

    Course insertCourse(Course theCourse);

    List<Course> insertMany(List<Course> courses);

    Course getCourse(int id);

    Course getCourseIdByName(String name);
}
