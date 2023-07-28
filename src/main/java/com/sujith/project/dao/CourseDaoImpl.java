package com.sujith.project.dao;

import com.sujith.project.entity.*;
import com.sujith.project.exceptions.*;
import jakarta.persistence.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class CourseDaoImpl implements CourseDao {

    private final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(CourseDaoImpl.class);

    @Autowired
    public CourseDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Course> getAll() {
        TypedQuery<Course> courseList = entityManager.createQuery("FROM Course ", Course.class);
        return courseList.getResultList();
    }

    @Override

    public Course insertCourse(Course theCourse) {
        Query temp = entityManager.createQuery("select count(*) from Course c where c.courseName=:thename ", Course.class);
        temp.setParameter("thename", theCourse.getCourseName());
        long count = (long) temp.getSingleResult();
        if (count == 0 && !theCourse.getCourseName().isEmpty()) {
            entityManager.persist(theCourse);
            return theCourse;
        } else {
            logger.error("Course already exist with name : {} ", theCourse.getCourseName());
            throw new ApiRequestException("Course alreasy exist with name : " + theCourse.getCourseName());
        }
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
        try {
            TypedQuery<Course> courseList = entityManager.createQuery("FROM Course c where c.id=:value  ", Course.class);
            courseList.setParameter("value", id);
            List<Course> temp = courseList.getResultList();
            return temp.get(0);
        } catch (Exception e) {
            logger.error("No course found with given id : {} ", id);
            throw new NoResultException("no course found");
        }

    }

    public Course getCourseIdByName(String name) {
        try {
            Query query = entityManager.createQuery("SELECT c FROM Course c WHERE c.courseName=:nameValue ");
            query.setParameter("nameValue", name);
            return (Course) query.getSingleResult();
        } catch (Exception e) {
            logger.error("Course does not exist with name : {} ", name);
            throw new NoResultException("Course does not exist");
        }
    }
}