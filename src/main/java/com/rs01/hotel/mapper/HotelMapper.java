package com.rs01.hotel.mapper;

import com.rs01.hotel.entity.Hotel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface HotelMapper {
    Hotel selectById(Long id);
    List<Hotel> selectAll();
    @Insert("""
            INSERT INTO hotel (name, address, phone, check_in_time, check_out_time, status)
            VALUES (#{name}, #{address}, #{phone}, #{checkInTime}, #{checkOutTime}, #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Hotel hotel);
    int update(Hotel hotel);
    int deleteById(Long id);
    List<Hotel> list(@Param("offset") long offset, @Param("limit") int limit);
}
