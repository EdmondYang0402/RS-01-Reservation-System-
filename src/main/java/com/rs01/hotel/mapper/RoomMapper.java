package com.rs01.hotel.mapper;

import com.rs01.hotel.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RoomMapper {
    Room selectById(Long id);
    int insert(Room room);
    int update(Room room);
    int deleteById(Long id);
    List<Room> listByHotelId(@Param("hotelId") Long hotelId, @Param("offset") long offset, @Param("limit") int limit);
}
