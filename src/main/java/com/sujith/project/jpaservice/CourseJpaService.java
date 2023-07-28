package com.sujith.project.jpaservice;

import com.sujith.project.dao.*;
import com.sujith.project.entity.*;
import com.sujith.project.exceptions.*;
import com.sujith.project.jpa.*;
import jakarta.transaction.*;
import org.apache.commons.lang.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CourseJpaService {
    CourseJpa courseJpa;
    CourseDao courseDao;
    Logger logger = LoggerFactory.getLogger(CourseJpaService.class);

    @Autowired
    public CourseJpaService(CourseJpa courseJpa, CourseDao courseDao) {
        this.courseJpa = courseJpa;
        this.courseDao = courseDao;
    }

    public List<Course> getAll() {
        List<Course> courses = courseJpa.findAll();
        if (courses.isEmpty()) {
            throw (new ApiRequestException("No Courses were found"));
        } else {
            return courses;
        }
    }


    @Transactional
    public Course insertCourse(Course theCourse) {
        try {
            return courseDao.insertCourse(theCourse);
        } catch (InvalidDataAccessApiUsageException ex) {
            throw new InvalidDataAccessApiUsageException("courseName should not be null");
        } catch (NullPointerException e) {
            throw new ApiRequestException("courseName should not be null");
        }
    }

    @Transactional
    public List<Course> insertMany(List<Course> courses) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses) {
            if (!StringUtils.isEmpty(course.getCourseName())) {
                result.add(course);
            } else {
                logger.error("Invalid coursename");
            }


        }
        return courseDao.insertMany(result);
    }
}
