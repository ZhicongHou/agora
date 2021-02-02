package com.hzcong.springboot.repository;


import com.hzcong.data.entities.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, String> {

    int deleteTaskEntityByTaskId(String id);

    boolean existsTaskEntityBySecIdAndStuName(String secId, String stuName);

    TaskEntity getTaskEntityByStuIdAndSecId(String stuId, String secId);

    Iterable<TaskEntity> getTaskEntitiesBySecId(String secId);

    Iterable<TaskEntity> getTaskEntitiesByStuId(String secId);

    @Query(value = "SELECT * FROM task WHERE stu_id= ?1 /*?#{#pageable}*/"
            ,countQuery="SELECT * FROM task WHERE stu_id= ?1"
            ,nativeQuery = true)
    Page<TaskEntity> findStudentTaskOfPage(String stuId, Pageable page);
}