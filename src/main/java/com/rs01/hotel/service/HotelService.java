package com.rs01.hotel.service;

import com.rs01.hotel.entity.Hotel;
import com.rs01.hotel.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelMapper hotelMapper;

    public Hotel getById(Long id) { return hotelMapper.selectById(id); }
    public List<Hotel> list(long offset, int limit) { return hotelMapper.list(offset, limit); }
    public int create(Hotel hotel) { return hotelMapper.insert(hotel); }
    public int update(Hotel hotel) { return hotelMapper.update(hotel); }
    public int delete(Long id) { return hotelMapper.deleteById(id); }
}
