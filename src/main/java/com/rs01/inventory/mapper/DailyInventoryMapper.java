package com.rs01.inventory.mapper;

import com.rs01.inventory.entity.DailyInventory;
import com.rs01.inventory.vo.AvailableRoomTypeVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DailyInventoryMapper {

    @Update("""
        UPDATE daily_inventory
            SET reserved_count = reserved_count - 1
            WHERE room_type_id = #{roomTypeId}
            AND stay_date = #{stayDate}
            AND reserved_count > 0
""")
    int releaseOne(Long roomTypeId, LocalDate stayDate);

    @Update("""
         UPDATE daily_inventory
            SET reserved_count = reserved_count + 1
            WHERE room_type_id = #{roomTypeId}
            AND stay_date = #{stayDate}
            AND total_inventory
              - reserved_count
              - out_of_service_count >= 1
""")
    int reserveOne(Long roomTypeId, LocalDate stayDate);

    @Select("""
            SELECT id,
                   room_type_id,
                   stay_date,
                   total_inventory,
                   reserved_count,
                   out_of_service_count,
                   create_time,
                   update_time
            FROM daily_inventory
            WHERE id = #{id}
            """)
    DailyInventory selectById(Long id);


    @Select("""
            SELECT id,
                   room_type_id,
                   stay_date,
                   total_inventory,
                   reserved_count,
                   out_of_service_count,
                   create_time,
                   update_time
            FROM daily_inventory
            WHERE room_type_id = #{roomTypeId}
              AND stay_date = #{stayDate}
            """)
    DailyInventory selectByRoomTypeIdAndStayDate(
            @Param("roomTypeId") Long roomTypeId,
            @Param("stayDate") LocalDate stayDate
    );


    @Select("""
            SELECT id,
                   room_type_id,
                   stay_date,
                   total_inventory,
                   reserved_count,
                   out_of_service_count,
                   create_time,
                   update_time
            FROM daily_inventory
            WHERE room_type_id = #{roomTypeId}
              AND stay_date >= #{startDate}
              AND stay_date < #{endDate}
            ORDER BY stay_date
            """)
    List<DailyInventory> selectByRoomTypeIdAndDateRange(
            @Param("roomTypeId") Long roomTypeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


    @Select("""
            SELECT rt.id AS room_type_id,
                   rt.name,
                   rt.capacity,
                   rt.bed_type,
                   rt.base_price,
                   #{checkInDate} AS check_in_date,
                   #{checkOutDate} AS check_out_date,
                   DATEDIFF(#{checkOutDate}, #{checkInDate}) AS night_count,
                   MIN(
                       di.total_inventory
                       - di.reserved_count
                       - di.out_of_service_count
                   ) AS available_rooms,
                   rt.base_price
                       * DATEDIFF(#{checkOutDate}, #{checkInDate}) AS total_amount
            FROM room_type rt
            JOIN daily_inventory di
              ON di.room_type_id = rt.id
            WHERE rt.status = 1
              AND rt.capacity >= #{guestCount}
              AND di.stay_date >= #{checkInDate}
              AND di.stay_date < #{checkOutDate}
            GROUP BY rt.id,
                     rt.name,
                     rt.capacity,
                     rt.bed_type,
                     rt.base_price
            HAVING COUNT(*) = DATEDIFF(#{checkOutDate}, #{checkInDate})
               AND MIN(
                   di.total_inventory
                   - di.reserved_count
                   - di.out_of_service_count
               ) > 0
            ORDER BY rt.base_price
            """)
    List<AvailableRoomTypeVO> selectAvailableRoomTypes(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("guestCount") Integer guestCount
    );


    @Insert("""
            INSERT INTO daily_inventory (
                room_type_id,
                stay_date,
                total_inventory,
                reserved_count,
                out_of_service_count
            )
            VALUES (
                #{roomTypeId},
                #{stayDate},
                #{totalInventory},
                #{reservedCount},
                #{outOfServiceCount}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DailyInventory inventory);


    @Update("""
            UPDATE daily_inventory
            SET total_inventory = #{totalInventory},
                reserved_count = #{reservedCount},
                out_of_service_count = #{outOfServiceCount},
                update_time = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int update(DailyInventory inventory);

    @Delete("""
            DELETE FROM daily_inventory
            WHERE id = #{id}
            """)
    int deleteById(Long id);
}
