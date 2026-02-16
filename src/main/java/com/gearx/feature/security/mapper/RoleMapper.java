package com.gearx.feature.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gearx.feature.security.entity.Role;

@Mapper
public interface RoleMapper {

    List<Role> findByUserId(@Param("userId") Integer userId);

    Role findByCode(@Param("roleCode") String roleCode);

//    int insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

//    int deleteUserRoles(@Param("userId") Integer userId);
}
