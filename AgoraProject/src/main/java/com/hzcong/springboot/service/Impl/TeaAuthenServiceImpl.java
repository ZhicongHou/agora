package com.hzcong.springboot.service.Impl;

import com.hzcong.data.entities.TeaAuthenticationEntity;
import com.hzcong.springboot.repository.TeaAuthenRepository;
import com.hzcong.springboot.service.TeaAuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeaAuthenServiceImpl implements TeaAuthenService {

    @Autowired
    private TeaAuthenRepository teaAuthenRepository;

    @Transactional
    public boolean addTeaAuthen(TeaAuthenticationEntity teaAuthentication){
        return teaAuthenRepository.save(teaAuthentication)!=null;
    }

    @Transactional
    public boolean deleteAuthenByUserId(String userId) {
        return teaAuthenRepository.deleteByUserId(userId)!=0;
    }

    @Transactional
    public boolean updateAuthorized(String userId, boolean authorized) {
        return teaAuthenRepository.updateAuthorizedByUserId(userId, authorized)!=0;
    }

    @Transactional
    public TeaAuthenticationEntity getTeaAuthenByUserId(String userId) {
        return teaAuthenRepository.getTeaAuthenByUserId(userId);
    }

    @Transactional
    public Page<TeaAuthenticationEntity> getAllTeaAuthenOfPage(int pageNum, int PageSize) {
        Pageable pageable = new PageRequest(pageNum,PageSize);
        return teaAuthenRepository.findAllTeaAuthenOfPage(pageable);
    }

    @Transactional
    public Page<TeaAuthenticationEntity> getAllAuthenedTeacherOfPage(int pageNum, int PageSize) {
        Pageable pageable = new PageRequest(pageNum,PageSize);
        return teaAuthenRepository.findAllAuthenedTeacherOfPage(pageable);
    }


    @Transactional
    public Page<TeaAuthenticationEntity> getAllUnauthenedTeacherOfPage(int pageNum, int PageSize) {
        Pageable pageable = new PageRequest(pageNum,PageSize);
        return teaAuthenRepository.findAllUnauthenedTeacherOfPage(pageable);
    }

    @Override
    public Iterable<TeaAuthenticationEntity> getAllUnauthenedTeachers() {
        return teaAuthenRepository.findAllUnauthenedTeachers();
    }

    @Override
    public Iterable<TeaAuthenticationEntity> getAllAuthenedTeachers() {
        return teaAuthenRepository.findAllAuthenedTeachers();
    }
}
