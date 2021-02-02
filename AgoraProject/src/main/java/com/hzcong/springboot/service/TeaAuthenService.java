package com.hzcong.springboot.service;

import com.hzcong.data.entities.TeaAuthenticationEntity;
import org.springframework.data.domain.Page;

public interface TeaAuthenService {

    boolean addTeaAuthen(TeaAuthenticationEntity teaAuthentication);

    boolean deleteAuthenByUserId(String userId);

    boolean updateAuthorized(String userId,boolean authorized);

    TeaAuthenticationEntity getTeaAuthenByUserId(String userId);

    Page<TeaAuthenticationEntity> getAllTeaAuthenOfPage(int pageNum, int PageSize);

    Page<TeaAuthenticationEntity> getAllAuthenedTeacherOfPage(int pageNum, int PageSize);

    Page<TeaAuthenticationEntity> getAllUnauthenedTeacherOfPage(int pageNum, int PageSize);

    Iterable<TeaAuthenticationEntity> getAllUnauthenedTeachers();

    Iterable<TeaAuthenticationEntity> getAllAuthenedTeachers();
}
