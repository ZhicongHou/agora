package com.hzcong.springboot.service.Impl;

import com.hzcong.data.entities.RoleEntity;
import com.hzcong.springboot.repository.RoleRepository;
import com.hzcong.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleEntity getRoleByRoleId(String roleId) {
        return roleRepository.findOne(roleId);
    }

    @Override
    public RoleEntity getRoleByRoleSign(String roleSign) {
        return roleRepository.getRoleEntityBySign(roleSign);
    }
}
