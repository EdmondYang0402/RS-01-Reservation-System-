USE rs01;

-- Development-only accounts. Passwords are BCrypt hashes; see docs/manual-test.md for local credentials.
INSERT INTO `user` (id, username, password, name, email, phone, role, status)
VALUES
    (1, 'customer', '$2a$10$ZU8cUUFJXEi7Bj7jvl0iCeh67WDH8A9Zhy4QeXF6sRyPMtBdLaS2m', 'Sakura Customer', 'customer@example.com', '13800000001', 'CUSTOMER', 1),
    (2, 'frontdesk', '$2a$10$7ThQQ5JHUL0Rq9SM2OTX/OWtoDj1O/e0XO6TZEck9/dnyGTB7RFe2', 'Sakura Front Desk', 'frontdesk@example.com', '13800000002', 'FRONT_DESK', 1),
    (3, 'admin', '$2a$10$UgjedYoDLSksLMIKPoaBpOkqAX0kCOSfLg3nKOq6XsqiSsZ6d7Cdq', 'Sakura Admin', 'admin@example.com', '13800000003', 'ADMIN', 1)
ON DUPLICATE KEY UPDATE
    password = VALUES(password), name = VALUES(name), email = VALUES(email),
    phone = VALUES(phone), role = VALUES(role), status = VALUES(status);

INSERT INTO hotel (id, name, address, phone, check_in_time, check_out_time, status)
VALUES (1, 'Sakura Inn', '1 Sakura Road', '000-0000-0000', '14:00:00', '12:00:00', 1)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO room_type
    (id, hotel_id, name, description, capacity, bed_type, base_price, total_rooms, status)
VALUES
    (1, 1, 'Single Room', 'Single room at Sakura Inn', 1, 'SINGLE', 299.00, 3, 1),
    (2, 1, 'Twin Room', 'Twin room at Sakura Inn', 2, 'TWIN', 399.00, 3, 1)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO room (id, hotel_id, room_type_id, room_number, floor, status)
VALUES
    (1, 1, 1, '101', 1, 'AVAILABLE'),
    (2, 1, 1, '102', 1, 'AVAILABLE'),
    (3, 1, 1, '103', 1, 'AVAILABLE'),
    (4, 1, 2, '201', 2, 'AVAILABLE'),
    (5, 1, 2, '202', 2, 'AVAILABLE'),
    (6, 1, 2, '203', 2, 'AVAILABLE')
ON DUPLICATE KEY UPDATE room_type_id = VALUES(room_type_id), status = VALUES(status);

INSERT INTO daily_inventory
    (room_type_id, stay_date, total_inventory, reserved_count, out_of_service_count)
SELECT room_type_id, DATE_ADD(CURRENT_DATE, INTERVAL day_offset DAY), 3, 0, 0
FROM (
    SELECT 1 AS room_type_id UNION ALL SELECT 2
) room_types
CROSS JOIN (
    SELECT 0 AS day_offset UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3
    UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7
    UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11
    UNION ALL SELECT 12 UNION ALL SELECT 13
) days
ON DUPLICATE KEY UPDATE total_inventory = VALUES(total_inventory);
