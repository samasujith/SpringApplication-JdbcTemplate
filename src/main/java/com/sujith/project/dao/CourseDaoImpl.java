package com.sujith.project.dao;

import com.sujith.project.entity.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class CourseDaoImpl implements CourseDao {



    JdbcTemplate jdbcTemplate;
    RowMapper<Course> courseRowMapper = (rs, rowNum) -> {
        Course course = new Course();
        course.setCourseName(rs.getString("course_name"));
        course.setId(rs.getInt("id"));
        return course;
    };

    @Autowired

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> getAll() {
        String query = "select * from course";
        return jdbcTemplate.query(query, courseRowMapper);
    }

    @Override

    public Course insertCourse(Course course) {

        String nextVal = "select count(id) from course";
        int next = jdbcTemplate.queryForObject(nextVal, Integer.class);

        next++;

        String courseQuery = "select count(course_name) from course where course_name=?";
        int count = jdbcTemplate.queryForObject(courseQuery, Integer.class, course.getCourseName());
        if (count > 0) {
            Course course1 = jdbcTemplate.queryForObject("select * from course c where c.course_name="
                    + "\"" + course.getCourseName() + "\"", courseRowMapper);
        } else {
            int totalCourses = jdbcTemplate.queryForObject("select count(id) from course", Integer.class);
            String countQuery = "INSERT INTO course ( id,course_name) VALUES (?, ?)";
            jdbcTemplate.update(countQuery, next, course.getCourseName());

        }

        return course;
    }

    @Override
    public List<Course> insertMany(List<Course> courses) {
        for (Course course : courses) {
            insertCourse(course);
        }
        return courses;
    }

    @Override
    public Course getCourse(int id) {
        String query = "select * from course c where c.id=" + id;
        return jdbcTemplate.queryForObject(query, Course.class);

    }

}