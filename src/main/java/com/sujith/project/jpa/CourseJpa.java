package com.sujith.project.jpa;

import com.sujith.project.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface CourseJpa extends JpaRepository<Course, Integer> {
}
