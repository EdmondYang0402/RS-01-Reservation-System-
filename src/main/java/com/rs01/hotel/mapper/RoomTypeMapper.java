package com.rs01.hotel.mapper;

import com.rs01.hotel.entity.RoomType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoomTypeMapper {
    @Select("""
        SELECT id,
               hotel_id,
               name,
               description,
               capacity,
               bed_type,
               base_price,
               total_rooms,
               status,
               create_time,
               update_time
        FROM room_type
        WHERE id = #{roomTypeId}
          AND status = 1
          AND capacity >= #{guestCount}
        """)
    RoomType selectBookableRoomType(
            @Param("roomTypeId") Long roomTypeId,
            @Param("guestCount") Integer guestCount
    );

    RoomType selectById(Long id);

    List<RoomType> selectByHotelId(Long hotelId);

    List<RoomType> selectAllEnabled();

    @Insert("""
            INSERT INTO room_type (
                hotel_id, name, description, capacity, bed_type, base_price, total_rooms, status
            )
            VALUES (
                #{hotelId}, #{name}, #{description}, #{capacity},
                #{bedType}, #{basePrice}, #{totalRooms}, #{status}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RoomType roomType);

    int update(RoomType roomType);

    int deleteById(Long id);

    List<RoomType> listByHotelId(@Param("hotelId") Long hotelId, @Param("offset") long offset, @Param("limit") int limit);
}
