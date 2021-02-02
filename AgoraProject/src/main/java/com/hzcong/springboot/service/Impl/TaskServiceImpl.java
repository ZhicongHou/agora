package com.hzcong.springboot.service.Impl;

import com.hzcong.data.entities.TaskEntity;
import com.hzcong.springboot.repository.TaskRepository;
import com.hzcong.springboot.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public Iterable<TaskEntity> getTasksByStuId(String stuId){
        return  taskRepository.getTaskEntitiesByStuId(stuId);
    }

    @Transactional
    public Iterable<TaskEntity> getAllTasks() {
        return  taskRepository.findAll();
    }

    @Transactional
    public TaskEntity saveTask(TaskEntity task){
        return taskRepository.save(task);
    }

    @Override
    public TaskEntity getTaskByStuIdAndSecId(String stuId, String secId) {
        return taskRepository.getTaskEntityByStuIdAndSecId(stuId, secId);
    }

    @Transactional
    public boolean deleteById(String secId){
        return taskRepository.deleteTaskEntityByTaskId(secId)!=0;
    }

    @Override
    public boolean existsTaskBySecIdAndStuName(String secId, String stuName) {
        return taskRepository.existsTaskEntityBySecIdAndStuName(secId,stuName);
    }

    @Transactional
    public Iterable<TaskEntity> getTasksBySecId(String secId){
        return taskRepository.getTaskEntitiesBySecId(secId);
    }

    /**
     * 分页查询学生的课程
     * @param stuId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<TaskEntity> getStudentTaskOfPage(String stuId, int pageNum, int pageSize) {
        Pageable page = new PageRequest(pageNum,pageSize);
        return taskRepository.findStudentTaskOfPage(stuId,page);
    }

}
