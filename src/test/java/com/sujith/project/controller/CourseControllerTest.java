package com.sujith.project.controller;

import com.sujith.project.controller_test_files.*;
import com.sujith.project.exceptions.*;
import com.sujith.project.jpaservice.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;

import java.nio.charset.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {
    @MockBean
    CourseJpaService courseService;
    @Autowired
    CourseController courseController;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("getAllCourses_valid")
    void getAllCourses_valid() throws Exception {
        when(courseService.getAll()).thenReturn(CourseDtoObject.getCourses());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses")).andExpect(status().isOk()).andExpect(result -> {
            CourseDtoObject.getCourses();
        });
    }

    @Test
    @DisplayName("getAllCourses_throwApiRequestException")
    void getAllCourses_throwApiRequestException() throws Exception {
        when(courseService.getAll()).thenThrow(new ApiRequestException("No Courses were found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses")).andExpect(status()
                .isNotFound()).andExpect(jsonPath("$.message").value("No Courses were found"));
    }

    @Test
    @DisplayName("insertCourse_valid")
    void insertCourse_valid() throws Exception {
        when(courseService.insertCourse(CourseDtoObject.getCourse())).thenReturn(CourseDtoObject.getCourse());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON).content(CourseDtoObject.getJson())).andExpect(status().isOk())
                .andExpect(result -> {
                    CourseDtoObject.getCourse();
                });
    }

    @Test
    @DisplayName("insertCourse_throwApiRequestException")
    void insertCourse_throwApiRequestException() throws Exception {
        when(courseService.insertCourse(CourseDtoObject.getInvalidCourse()))
                .thenThrow(new ApiRequestException("courseName should not be null"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/courses").characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON).content(CourseDtoObject.getInvalidJson()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("insertManyCourses_valid")
    void insertManyCourse_valid() throws Exception {
        when(courseService.insertMany(CourseDtoObject.getCourses())).thenReturn(CourseDtoObject.getCourses());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/courses/insertMany").characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON).content(CourseDtoObject.getJsonList())).andExpect(status().isOk())
                .andExpect(result -> {
                    CourseDtoObject.getCourse();
                });
    }

    @Test
    @DisplayName("insertManyCourses_throwApiRequestException")
    void insertManyCourses_throwApiRequestException() throws Exception {
        when(courseService.insertMany(CourseDtoObject.getInvalidCourseList())).thenThrow(ApiRequestException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/courses/insertMany").characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON).content(CourseDtoObject.getInvalidJsonList()))
                .andExpect(status().isNotFound());
    }


}
