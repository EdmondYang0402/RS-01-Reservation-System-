package com.rs01.hotel.mapper;

import com.rs01.hotel.entity.Hotel;
import com.rs01.hotel.vo.AvailableHotelVO;
import com.rs01.hotel.vo.HotelListVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface HotelMapper {
    @Select("""
            SELECT h.id AS hotel_id,
                   h.name AS hotel_name,
                   h.address,
                   COUNT(*) AS available_room_type_count,
                   MIN(
                       available_rt.base_price
                       * DATEDIFF(#{checkOutDate}, #{checkInDate})
                   ) AS min_total_amount
            FROM hotel h

            JOIN (
                SELECT rt.id AS room_type_id,
                       rt.hotel_id,
                       rt.base_price
                FROM room_type rt

                JOIN daily_inventory di
                  ON di.room_type_id = rt.id

                WHERE rt.status = 1
                  AND rt.capacity >= #{guestCount}

                  AND di.stay_date >= #{checkInDate}
                  AND di.stay_date < #{checkOutDate}

                GROUP BY rt.id,
                         rt.hotel_id,
                         rt.base_price

                HAVING COUNT(*) =
                           DATEDIFF(#{checkOutDate}, #{checkInDate})

                   AND MIN(
                       di.total_inventory
                       - di.reserved_count
                       - di.out_of_service_count
                   ) > 0

            ) available_rt
              ON available_rt.hotel_id = h.id

            WHERE h.status = 1

              AND (
                    #{keyword} IS NULL
                    OR #{keyword} = ''
                    OR h.name LIKE CONCAT('%', #{keyword}, '%')
                    OR h.address LIKE CONCAT('%', #{keyword}, '%')
              )

            GROUP BY h.id,
                     h.name,
                     h.address

            ORDER BY min_total_amount ASC,
                     h.id ASC
            """)
    List<AvailableHotelVO> selectAvailableHotels(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("guestCount") Integer guestCount,
            @Param("keyword") String keyword
    );


    @Select("""

        SELECT h.id AS hotel_id,
               h.name AS hotel_name,
               h.address,
               h.cover_image_url,
               MIN(rt.base_price) AS min_base_price
        FROM hotel h
        LEFT JOIN room_type rt
          ON rt.hotel_id = h.id
         AND rt.status = 1
        WHERE h.status = 1
        GROUP BY h.id,
                 h.name,
                 h.address,
                 h.cover_image_url
        ORDER BY h.id;
        """)
    List<HotelListVO> selectEnabledHotels();

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
