package com.gearx.feature.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gearx.common.base.mapper.BaseMapper;
import com.gearx.feature.security.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findByUsername(@Param("username") String username);

    User findByEmail(@Param("email") String email);

    boolean existsById(@Param("id") int userId);

    int existsByUsername(@Param("username") String username);

    int existsByEmail(@Param("email") String email);

    int updatePasswordById(
            @Param("email") String email,
            @Param("userId") Integer userId,
            @Param("password") String hashed);

    int deleteUserById(@Param("userId") Integer userId);

    int updateUserById(@Param("userId") Integer userId, User user);
}
