package com.rs01.reservation.mapper;

import com.rs01.reservation.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ReservationMapper {
    Reservation selectById(Long id);
    Reservation selectByReservationNo(String reservationNo);
    int insert(Reservation reservation);
    List<Reservation> list(@Param("userId") Long userId, @Param("offset") long offset, @Param("limit") int limit);
}
