package com.rs01.reservation.mapper;

import com.rs01.reservation.entity.Reservation;
import com.rs01.reservation.entity.ReservationStatus;
import com.rs01.reservation.vo.ReservationDetailVO;
import com.rs01.reservation.vo.ReservationListVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReservationMapper {
    Reservation selectById(Long id);

    Reservation selectByReservationNo(String reservationNo);

    @Update("""
        UPDATE reservation
        SET status = 'CHECKED_IN',
            assigned_room_id = #{roomId},
            actual_check_in_time = CURRENT_TIMESTAMP,
            update_time = CURRENT_TIMESTAMP
        WHERE id = #{reservationId}
          AND status = 'CONFIRMED'
        """)
    int checkInReservation(
            @Param("reservationId") Long reservationId,
            @Param("roomId") Long roomId
    );

    @Insert("""
            INSERT INTO reservation (
                reservation_no, user_id, hotel_id, room_type_id, assigned_room_id,
                check_in_date, check_out_date, guest_name, guest_phone, guest_count,
                total_amount, status, actual_check_in_time, actual_check_out_time
            )
            VALUES (
                #{reservationNo}, #{userId}, #{hotelId}, #{roomTypeId}, #{assignedRoomId},
                #{checkInDate}, #{checkOutDate}, #{guestName}, #{guestPhone}, #{guestCount},
                #{totalAmount}, #{status}, #{actualCheckInTime}, #{actualCheckOutTime}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Reservation reservation);

    @Select("""
        SELECT id,
               reservation_no,
               user_id,
               hotel_id,
               room_type_id,
               assigned_room_id,
               check_in_date,
               check_out_date,
               guest_name,
               guest_phone,
               guest_count,
               total_amount,
               status,
               actual_check_in_time,
               actual_check_out_time,
               create_time,
               update_time
        FROM reservation
        WHERE id = #{reservationId}
          AND user_id = #{userId}
        """)
    Reservation selectByIdAndUserId(
            @Param("reservationId") Long reservationId,
            @Param("userId") Long userId
    );

    @Update("""
        UPDATE reservation
        SET status = #{newStatus},
            update_time = CURRENT_TIMESTAMP
        WHERE id = #{id}
          AND status = #{oldStatus}
        """)
    int updateStatus(
            @Param("id") Long id,
            @Param("oldStatus") ReservationStatus oldStatus,
            @Param("newStatus") ReservationStatus newStatus
    );


    List<ReservationListVO> selectByUserId(Long userId);

    ReservationDetailVO selectDetailById(Long reservationId);

    List<ReservationListVO> selectArrivalsByDate(LocalDate arrivalDate);

    List<ReservationListVO> selectDeparturesByDate(LocalDate departureDate);


    int update(Reservation reservation);

    default List<ReservationListVO> selectArrivals(LocalDate arrivalDate) {
        return selectArrivalsByDate(arrivalDate);
    }

    default List<ReservationListVO> selectDepartures(LocalDate departureDate) {
        return selectDeparturesByDate(departureDate);
    }
}
