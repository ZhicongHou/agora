package com.hzcong.springboot.service;

import com.hzcong.data.entities.RoleEntity;

public interface RoleService {

    RoleEntity getRoleByRoleId(String roleId);
    RoleEntity getRoleByRoleSign(String roleSign);


}
