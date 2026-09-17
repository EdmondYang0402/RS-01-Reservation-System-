package com.rs01.user.mapper;

import com.rs01.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    User selectById(Long id);
    int insert(User user);
    int update(User user);
    int deleteById(Long id);
    List<User> list(@Param("offset") long offset, @Param("limit") int limit);
}
