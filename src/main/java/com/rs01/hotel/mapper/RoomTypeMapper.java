package com.rs01.hotel.mapper;

import com.rs01.hotel.entity.RoomType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RoomTypeMapper {
    RoomType selectById(Long id);
    int insert(RoomType roomType);
    int update(RoomType roomType);
    int deleteById(Long id);
    List<RoomType> listByHotelId(@Param("hotelId") Long hotelId, @Param("offset") long offset, @Param("limit") int limit);
}
