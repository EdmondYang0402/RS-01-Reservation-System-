package com.rs01.hotel.mapper;

import com.rs01.hotel.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface HotelMapper {
    Hotel selectById(Long id);
    int insert(Hotel hotel);
    int update(Hotel hotel);
    int deleteById(Long id);
    List<Hotel> list(@Param("offset") long offset, @Param("limit") int limit);
}
