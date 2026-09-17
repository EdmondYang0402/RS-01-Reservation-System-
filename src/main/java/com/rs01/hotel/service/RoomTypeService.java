package com.rs01.hotel.service;

import com.rs01.hotel.entity.RoomType;
import com.rs01.hotel.mapper.RoomTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomTypeService {
    private final RoomTypeMapper roomTypeMapper;

    public RoomType getById(Long id) { return roomTypeMapper.selectById(id); }
    public List<RoomType> listByHotelId(Long hotelId, long offset, int limit) { return roomTypeMapper.listByHotelId(hotelId, offset, limit); }
    public int create(RoomType roomType) { return roomTypeMapper.insert(roomType); }
    public int update(RoomType roomType) { return roomTypeMapper.update(roomType); }
    public int delete(Long id) { return roomTypeMapper.deleteById(id); }
}
