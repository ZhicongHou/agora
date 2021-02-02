package com.hzcong.springboot.service;

import com.hzcong.data.entities.UserEntity;
import org.springframework.data.domain.Page;

public interface UserService {

    boolean save(UserEntity user);

    boolean update(UserEntity user);

    Iterable<UserEntity> getAll();

    UserEntity getUserByUserId(String id);

    UserEntity getUserByUserName(String userName);

    UserEntity getUserByEmail(String email);

    boolean deleteByUserName(String userName);

    boolean  updateActived(boolean actived,String userId);

    Iterable<UserEntity> getUsersBySecId(String secId);

    Iterable<UserEntity> getUsersByRoleName(String name);

    Iterable<String> getRoleSignsByUserId(String userId);

    Iterable<String> getRoleSignsByUserName(String userName);

    Page<UserEntity> getAllUsersOfPage(int pageNum, int pageSize);

    boolean updateProhibited(boolean prohibited,String userId);
}
