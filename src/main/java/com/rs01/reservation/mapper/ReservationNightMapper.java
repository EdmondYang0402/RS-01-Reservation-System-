package com.rs01.reservation.mapper;

import com.rs01.reservation.entity.ReservationNight;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationNightMapper {
    ReservationNight selectById(Long id);

    @Insert("""
            INSERT INTO reservation_night (reservation_id, stay_date, price)
            VALUES (#{reservationId}, #{stayDate}, #{price})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReservationNight reservationNight);

    int batchInsert(@Param("nights") List<ReservationNight> nights);

    List<ReservationNight> selectByReservationId(Long reservationId);

    int deleteByReservationId(Long reservationId);
}
