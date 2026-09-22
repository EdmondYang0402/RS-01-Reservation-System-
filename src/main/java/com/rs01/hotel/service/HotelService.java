package com.rs01.hotel.service;

import com.rs01.common.exception.BusinessException;
import com.rs01.hotel.dto.HotelAvailabilitySearchDTO;
import com.rs01.hotel.entity.Hotel;
import com.rs01.hotel.mapper.HotelMapper;
import com.rs01.hotel.vo.AvailableHotelVO;
import com.rs01.inventory.dto.AvailabilitySearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelMapper hotelMapper;

    public List<AvailableHotelVO> searchAvailableHotels(HotelAvailabilitySearchDTO dto) {

        LocalDate checkInDate = dto.getCheckInDate();
        LocalDate checkOutDate = dto.getCheckOutDate();
        Integer guestCount = dto.getGuestCount();

        if (checkInDate == null || checkOutDate == null) {
            throw new BusinessException("入住日期和退房日期不能为空");
        }

        if (checkInDate.isBefore(LocalDate.now())) {
            throw new BusinessException("入住日期不能早于今天");
        }

        if (!checkOutDate.isAfter(checkInDate)) {
            throw new BusinessException("退房日期必须晚于入住日期");
        }

        if (guestCount == null || guestCount <= 0) {
            throw new BusinessException("入住人数必须大于0");
        }

        String keyword = dto.getKeyword();

        if (keyword != null) {
            keyword = keyword.trim();
        }

        return hotelMapper.selectAvailableHotels(checkInDate, checkOutDate, guestCount, keyword);
    }

    public Hotel getById(Long id) { return hotelMapper.selectById(id); }
    public List<Hotel> list(long offset, int limit) { return hotelMapper.list(offset, limit); }
    public int create(Hotel hotel) { return hotelMapper.insert(hotel); }
    public int update(Hotel hotel) { return hotelMapper.update(hotel); }
    public int delete(Long id) { return hotelMapper.deleteById(id); }
}

