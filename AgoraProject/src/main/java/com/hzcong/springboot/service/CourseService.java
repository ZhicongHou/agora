package com.hzcong.springboot.service;

import com.hzcong.data.entities.CourseEntity;
import org.springframework.data.domain.Page;

public interface CourseService {


    Iterable<CourseEntity> getAllCourses();

    boolean addCourse(CourseEntity course);

    boolean deleteCourseById(String id);

    CourseEntity getCourseById(String courseId);

    Page<CourseEntity> getCoursesOfPage(int pageNum, int pageSize);
}
