package com.hzcong.springboot.service;

import com.hzcong.data.entities.TaskEntity;
import org.springframework.data.domain.Page;

public interface TaskService {


    TaskEntity saveTask(TaskEntity task);

    boolean deleteById(String userId);

    boolean existsTaskBySecIdAndStuName(String secId, String stuName);

    TaskEntity getTaskByStuIdAndSecId(String stuId,String secId);


    Iterable<TaskEntity> getAllTasks();

    Iterable<TaskEntity> getTasksByStuId(String stuId);

    Iterable<TaskEntity> getTasksBySecId(String secId);

    Page<TaskEntity> getStudentTaskOfPage(String stuId, int pageNum, int pageSize);
}
