package com.rs01.reservation;

import com.rs01.hotel.mapper.RoomMapper;
import com.rs01.security.CustomUserPrincipal;
import com.rs01.security.JwtUtil;
import com.rs01.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BusinessScaffoldIntegrationTests {
    @Autowired private MockMvc mockMvc;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private RoomMapper roomMapper;

    @BeforeEach
    void seedOrdinaryQueryData() {
        jdbcTemplate.update("DELETE FROM reservation_night");
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM daily_inventory");
        jdbcTemplate.update("DELETE FROM room");
        jdbcTemplate.update("DELETE FROM room_type");
        jdbcTemplate.update("DELETE FROM hotel");
        jdbcTemplate.update("DELETE FROM `user` WHERE id = 900");

        jdbcTemplate.update("INSERT INTO `user` (id, username, password, name, role, status) VALUES (900, 'scaffold-user', '$2a$10$test', 'Scaffold User', 'CUSTOMER', 1)");
        jdbcTemplate.update("INSERT INTO hotel (id, name, address, phone, check_in_time, check_out_time, status) VALUES (900, 'Test Hotel', 'Test Address', '10000', '14:00:00', '12:00:00', 1)");
        jdbcTemplate.update("INSERT INTO room_type (id, hotel_id, name, capacity, bed_type, base_price, total_rooms, status) VALUES (900, 900, 'Twin Room', 2, 'TWIN', 399.00, 2, 1)");
        jdbcTemplate.update("INSERT INTO room (id, hotel_id, room_type_id, room_number, floor, status) VALUES (900, 900, 900, '201', 2, 'AVAILABLE')");
        jdbcTemplate.update("INSERT INTO room (id, hotel_id, room_type_id, room_number, floor, status) VALUES (901, 900, 900, '202', 2, 'OCCUPIED')");
        jdbcTemplate.update("""
                INSERT INTO reservation (id, reservation_no, user_id, hotel_id, room_type_id,
                check_in_date, check_out_date, guest_name, guest_phone, guest_count, total_amount, status)
                VALUES (900, 'R-TEST-900', 900, 900, 900, ?, ?, 'Test Guest', '10086', 2, 399.00, 'CONFIRMED')
                """, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(1)));
        jdbcTemplate.update("INSERT INTO reservation_night (reservation_id, stay_date, price) VALUES (900, ?, ?)",
                Date.valueOf(LocalDate.now()), new BigDecimal("399.00"));
    }

    @Test
    void ordinaryReservationRoomFrontDeskAndInventoryQueriesWork() throws Exception {
        String customerToken = token(900L, "scaffold-user", UserRole.CUSTOMER);
        mockMvc.perform(get("/reservations").header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].reservationId").value(900))
                .andExpect(jsonPath("$.data[0].guestName").value("Test Guest"));

        mockMvc.perform(get("/reservations/900").header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.reservationNo").value("R-TEST-900"))
                .andExpect(jsonPath("$.data.nights[0].price").value(399.00));

        assertThat(roomMapper.selectByRoomTypeId(900L)).hasSize(2);
        assertThat(roomMapper.selectAvailableByRoomTypeId(900L)).hasSize(1);

        String frontDeskToken = token(901L, "front-desk", UserRole.FRONT_DESK);
        mockMvc.perform(get("/front-desk/arrivals").header("Authorization", "Bearer " + frontDeskToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].reservationId").value(900));

        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = start.plusDays(2);
        String adminToken = token(902L, "admin", UserRole.ADMIN);
        String body = "{\"roomTypeId\":900,\"startDate\":\"" + start + "\",\"endDate\":\"" + end + "\"}";
        mockMvc.perform(post("/admin/inventory/initialize")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(2));
        mockMvc.perform(post("/admin/inventory/initialize")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(0));
    }

    private String token(Long userId, String username, UserRole role) {
        return jwtUtil.generateToken(new CustomUserPrincipal(userId, username, null, role, true));
    }
}
