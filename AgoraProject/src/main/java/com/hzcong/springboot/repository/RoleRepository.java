package com.hzcong.springboot.repository;

import com.hzcong.data.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity,String> {

    RoleEntity getRoleEntityByName(String name);



    RoleEntity getRoleEntityBySign(String sign);



}
