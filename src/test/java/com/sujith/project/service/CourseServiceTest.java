package com.sujith.project.service;

import com.sujith.project.dao.*;
import com.sujith.project.entity.*;
import com.sujith.project.exceptions.*;
import com.sujith.project.jpa.*;
import com.sujith.project.jpaservice.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.dao.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseServiceTest {
    static List<Course> validCourses = new ArrayList<>();
    static Course validCourse = Course.builder().courseName("C").id(1).build();
    static List<Course> invalidCourses = new ArrayList<>();
    static Course invalidCourse = new Course();
    @MockBean
    CourseDao courseDao;
    @MockBean
    CourseJpa courseJpa;
    @Autowired
    CourseJpaService courseService;

    @BeforeAll
    static void beforeAll() {
        validCourses.add(Course.builder().courseName("C").id(1).build());
        validCourses.add(Course.builder().courseName("java").id(2).build());
        invalidCourses.add(new Course());
        invalidCourses.add(Course.builder().courseName("C").build());
    }

    @Test
    @DisplayName("getAllCourses_valid")
    void getAllCourses_valid() {
        when(courseJpa.findAll()).thenReturn(validCourses);
        assertEquals(validCourses, courseService.getAll());
    }

    @Test
    @DisplayName("getAllCourses_throwApiRequestException")
    void getAllCourses_throwApiRequestException() {
        when(courseJpa.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ApiRequestException.class, () -> courseService.getAll());
    }

    @Test
    @DisplayName("insertCourse_valid")
    void inserCourse_valid() {
        when(courseDao.insertCourse(validCourse)).thenReturn(validCourse);
        assertEquals(validCourse, courseService.insertCourse(validCourse));
    }

    @Test
    @DisplayName("insertCourse_throwInvalidDataAccessApiUsageException")
    void insertCourse_throwInvalidDataAccessApiUsageException() {
        when(courseDao.insertCourse(invalidCourse)).thenThrow(InvalidDataAccessApiUsageException.class);

        assertThrows(InvalidDataAccessApiUsageException.class, () -> courseService.insertCourse(invalidCourse));
    }

    @Test
    @DisplayName("insertCourse_throwNullPointerException")
    void insertCourse_throwNullPointerException() {
        when(courseDao.insertCourse(invalidCourse)).thenThrow(NullPointerException.class);

        assertThrows(ApiRequestException.class, () -> courseService.insertCourse(invalidCourse));
    }

    @Test
    @DisplayName("insertLIstOfCourse_valid")
    void insertListOfCourse_valid() {
        when(courseDao.insertMany(validCourses)).thenReturn(validCourses);
        assertEquals(validCourses, courseService.insertMany(validCourses));
    }


    @Test
    @DisplayName("insertListOfCourse_NoCourseName")
    void insertListOfCourse_notValid() {
        List<Course> tempCourse = new ArrayList<>();
        tempCourse.add(Course.builder().courseName("C").build());
        when(courseDao.insertMany(tempCourse)).thenReturn(tempCourse);
        assertEquals(tempCourse, courseService.insertMany(invalidCourses));
    }


}
