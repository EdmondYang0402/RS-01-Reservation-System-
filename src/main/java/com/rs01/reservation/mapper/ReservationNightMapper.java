package com.rs01.reservation.mapper;

import com.rs01.reservation.entity.ReservationNight;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReservationNightMapper {
    int insert(ReservationNight reservationNight);
    List<ReservationNight> selectByReservationId(Long reservationId);
}
