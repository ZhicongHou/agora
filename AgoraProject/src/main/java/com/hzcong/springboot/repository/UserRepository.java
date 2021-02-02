package com.hzcong.springboot.repository;

import com.hzcong.data.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

    UserEntity getUserEntityById(String id);

    UserEntity getUserEntityByUserName(String userName);

    UserEntity getUserEntityByEmail(String email);

    int deleteUserEntityByUserName(String userName);

    int deleteUserEntityById(String id);

//    @Query(value = "SELECT * FROM user_role join user on user.id = user_role.user_id WHERE user_role.role_id =:roleId",nativeQuery = true)
//    Iterable<UserEntity> findRole(@Param("roleId") String roleId);

    @Query(value = "SELECT * FROM user_role join user on user.id = user_role.user_id WHERE user_role.role_id =:roleId",nativeQuery = true)
    Iterable<UserEntity> getUserEntitiesByRoleId(@Param("roleId") String roleId);

    @Modifying
    @Query(value = "update user set actived = :actived where id = :userId",nativeQuery = true)
    int updateActivedByUserId(@Param("actived")boolean actived,@Param("userId")String userId);

    @Query(value = "SELECT role.sign FROM user_role join user on user.id = user_role.user_id join role on user_role.role_id = role.id WHERE user.id =:userId",nativeQuery = true)
    Iterable<String> getRoleSignsByUserId(@Param("userId")String userId);

    @Query(value = "SELECT user_role.role_id FROM user_role join user on user.id = user_role.user_id WHERE user.id =:userId",nativeQuery = true)
    Iterable<String> getRoleIdsByUserId(@Param("userId")String userId);

    @Query(value = "SELECT * FROM user_role join user on user.id = user_role.user_id WHERE user_role.role_id = ?1/*?#{#pageable}*/"
            ,countQuery="select count(*) from user_role join user on user.id = user_role.user_id WHERE user_role.role_id = ?1"
            ,nativeQuery = true)
    Page<UserEntity> getAllRoleUsers(String roleId, Pageable page);


    @Query(value = "SELECT * FROM user /*?#{#pageable}*/"
            ,countQuery="select count(*) FROM user "
            ,nativeQuery = true)
    Page<UserEntity> findAllUsersOfPage(Pageable page);

    @Modifying
    @Query(value = "update user set pohibited = :pohibited where id = :userId",nativeQuery = true)
    int updatePohibitedByUserId(@Param("pohibited")boolean pohibited,@Param("userId")String userId);
}
