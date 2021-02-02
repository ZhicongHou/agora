package com.hzcong.springboot.repository;


import com.hzcong.data.entities.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<CourseEntity, String> {

    @Modifying
    int deleteCourseEntityById(String id);

    @Query(value = "SELECT * FROM course /*?#{#pageable}*/"
            ,countQuery="SELECT * FROM course "
            ,nativeQuery = true)
    Page<CourseEntity> findAllCoursesOfPage(Pageable page);


}