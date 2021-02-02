package com.hzcong.springboot.repository;

import com.hzcong.data.entities.TeaAuthenticationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TeaAuthenRepository extends CrudRepository<TeaAuthenticationEntity,String> {

    @Modifying
    @Query(value = "update tea_authentication set authorized = :authorized where user_id = :userId",nativeQuery = true)
    int updateAuthorizedByUserId(@Param("userId")String userId, @Param("authorized") boolean authorized);

    @Query(value = "select * from tea_authentication where user_id = :userId",nativeQuery = true)
    TeaAuthenticationEntity getTeaAuthenByUserId(@Param("userId")String userId);

    @Modifying
    int deleteByUserId(String userId);

    @Query(value = "SELECT * FROM tea_authentication/*?#{#pageable}*/"
            ,countQuery="SELECT count(*) FROM tea_authentication"
            ,nativeQuery = true)
    Page<TeaAuthenticationEntity> findAllTeaAuthenOfPage(Pageable page);

    @Query(value = "SELECT * FROM tea_authentication WHERE authorized = 1/*?#{#pageable}*/"
            ,countQuery="SELECT count(*) FROM tea_authentication WHERE authorized = 1"
            ,nativeQuery = true)
    Page<TeaAuthenticationEntity> findAllAuthenedTeacherOfPage(Pageable page);


    @Query(value = "SELECT * FROM tea_authentication WHERE authorized = 0/*?#{#pageable}*/"
            ,countQuery="SELECT count(*) FROM tea_authentication WHERE authorized = 0"
            ,nativeQuery = true)
    Page<TeaAuthenticationEntity> findAllUnauthenedTeacherOfPage(Pageable page);

    @Query(value = "SELECT * FROM tea_authentication WHERE authorized = 1"
            ,countQuery="SELECT count(*) FROM tea_authentication WHERE authorized = 1"
            ,nativeQuery = true)
    Iterable<TeaAuthenticationEntity> findAllAuthenedTeachers();


    @Query(value = "SELECT * FROM tea_authentication WHERE authorized = 0"
            ,countQuery="SELECT count(*) FROM tea_authentication WHERE authorized = 0"
            ,nativeQuery = true)
    Iterable<TeaAuthenticationEntity> findAllUnauthenedTeachers();
}
