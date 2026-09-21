package com.rs01.user.mapper;

import com.rs01.user.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User selectById(Long id);

    User selectByUsername(String username);

    boolean existsByUsername(String username);

    @Insert("""
            INSERT INTO `user` (username, password, name, email, phone, role, status)
            VALUES (#{username}, #{password}, #{name}, #{email}, #{phone}, #{role}, #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    int updateBasicInfo(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("email") String email,
            @Param("phone") String phone
    );

    int updatePassword(@Param("id") Long id, @Param("password") String password);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
