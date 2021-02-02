package com.hzcong.springboot.service.Impl;

import com.hzcong.data.entities.RoleEntity;
import com.hzcong.data.entities.TaskEntity;
import com.hzcong.data.entities.UserEntity;
import com.hzcong.springboot.repository.RoleRepository;
import com.hzcong.springboot.repository.TaskRepository;
import com.hzcong.springboot.repository.UserRepository;
import com.hzcong.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean save(UserEntity user) {
        return userRepository.save(user)!=null;
    }

    @Override
    public Iterable<UserEntity> getAll() {
        return  userRepository.findAll();
    }

    @Override
    public UserEntity getUserByUserName(String userName) {
        return userRepository.getUserEntityByUserName(userName);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.getUserEntityByEmail(email);
    }

    @Override
    @Transactional
    public boolean deleteByUserName(String userName) {
        return userRepository.deleteUserEntityByUserName(userName)!=0;
    }

    @Override
    public Iterable<UserEntity> getUsersBySecId(String secId) {
        Iterable<TaskEntity> list = taskRepository.getTaskEntitiesBySecId(secId);
        List<UserEntity> users = new ArrayList<>();
        for (TaskEntity taskEntity :  list) {
            UserEntity entity = userRepository.findOne(taskEntity.getStuId());
            users.add(entity);
        }
        return users;
    }

    @Override
    public boolean update(UserEntity user) {
        UserEntity target = userRepository.getUserEntityByUserName(user.getUserName());
        if(target==null){
            return false;
        }
        target.setEmail(user.getEmail());
        target.setPassword(user.getPassword());
        target.setRealName(user.getRealName());
        target.setSex(user.getSex());
        return true;
    }

    @Override
    public Iterable<UserEntity> getUsersByRoleName(String roleName) {
        RoleEntity role = roleRepository.getRoleEntityByName(roleName);
        if(role != null){
            return  userRepository.getUserEntitiesByRoleId(role.getId());
        }else {
            return  null;
        }
    }

    @Override
    public UserEntity getUserByUserId(String id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional
    public boolean updateActived(boolean actived, String userId) {
        return userRepository.updateActivedByUserId(actived, userId)!=0;
    }

    @Transactional
    @Override
    public Iterable<String> getRoleSignsByUserId(String userId) {
        return userRepository.getRoleSignsByUserId(userId);
    }

    @Override
    public Iterable<String> getRoleSignsByUserName(String userName) {
        UserEntity user = userRepository.getUserEntityByUserName(userName);
        return userRepository.getRoleSignsByUserId(user.getId());
    }

    /**
     * 分页查看用户信息
     * @return
     */
    @Transactional
    public Page<UserEntity> getAllUsersOfPage(int pageNum, int pageSize) {
        Pageable page=new PageRequest(pageNum,pageSize);
        return  userRepository.findAllUsersOfPage(page);
    }

    @Override
    public boolean updateProhibited(boolean prohibited, String userId) {
        return false;
    }


}
