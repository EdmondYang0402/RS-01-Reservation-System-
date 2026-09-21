package com.rs01.hotel.service;

import com.rs01.hotel.entity.Room;
import com.rs01.hotel.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomMapper roomMapper;

    public Room getById(Long id) { return roomMapper.selectById(id); }
    public List<Room> listByHotelId(Long hotelId, long offset, int limit) { return roomMapper.listByHotelId(hotelId, offset, limit); }
    public List<Room> listAvailableByRoomTypeId(Long roomTypeId) { return roomMapper.selectAvailableByRoomTypeId(roomTypeId); }
    public int create(Room room) { return roomMapper.insert(room); }
    public int update(Room room) { return roomMapper.update(room); }
    public int delete(Long id) { return roomMapper.deleteById(id); }
}
