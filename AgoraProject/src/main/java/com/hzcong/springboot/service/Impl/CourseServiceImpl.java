package com.hzcong.springboot.service.Impl;


import com.hzcong.data.entities.CourseEntity;
import com.hzcong.springboot.repository.CourseRepository;
import com.hzcong.springboot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public Page<CourseEntity> getCoursesOfPage(int pageNum,int pageSize){
        Pageable page = new PageRequest(pageNum,pageSize);
        return courseRepository.findAllCoursesOfPage(page);
    }

    @Transactional
    public Iterable<CourseEntity> getAllCourses(){
        return  courseRepository.findAll();
    }

    @Transactional
    public boolean addCourse(CourseEntity course){
        return courseRepository.save(course)!=null;
    }

    @Transactional
    public boolean deleteCourseById(String id){
        return courseRepository.deleteCourseEntityById(id)!=0;
    }

    @Override
    public CourseEntity getCourseById(String courseId) {
        return courseRepository.findOne(courseId);
    }

}
