package com.rs01.hotel.mapper;

import com.rs01.hotel.entity.Room;
import com.rs01.hotel.entity.RoomStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoomMapper {

    Room selectById(Long id);


    List<Room> selectByHotelId(Long hotelId);


    List<Room> selectByRoomTypeId(Long roomTypeId);


    List<Room> selectAvailableByRoomTypeId(Long roomTypeId);


    @Insert("""
            INSERT INTO room (
                hotel_id,
                room_type_id,
                room_number,
                floor,
                status
            )
            VALUES (
                #{hotelId},
                #{roomTypeId},
                #{roomNumber},
                #{floor},
                #{status}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Room room);


    int update(Room room);


    int updateStatus(
            @Param("id") Long id,
            @Param("status") RoomStatus status
    );


    int deleteById(Long id);


    List<Room> listByHotelId(
            @Param("hotelId") Long hotelId,
            @Param("offset") long offset,
            @Param("limit") int limit
    );


    // checkIn 核心业务使用：
    // 只有房间当前仍然 AVAILABLE 时才能占用
    @Update("""
            UPDATE room
            SET status = 'OCCUPIED',
                update_time = CURRENT_TIMESTAMP
            WHERE id = #{roomId}
              AND status = 'AVAILABLE'
            """)
    int occupyRoom(@Param("roomId") Long roomId);
}
