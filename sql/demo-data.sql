USE rs01;

-- RS-01 multi-hotel development demo data.
-- Safe to execute repeatedly: natural keys are queried and rows are upserted.
-- Inventory covers 2026-09-22 through 2026-11-20 (60 stay dates).
-- No reservation or reservation_night rows are inserted by this script.

-- -----------------------------------------------------------------------------
-- Development accounts (BCrypt hashes; never plaintext in the database)
-- customer/customer2: Customer123!
-- frontdesk/frontdesk2: FrontDesk123!
-- admin: Admin123!
-- -----------------------------------------------------------------------------
INSERT INTO `user` (username, password, name, email, phone, role, status)
VALUES
    ('customer', '$2a$10$ZU8cUUFJXEi7Bj7jvl0iCeh67WDH8A9Zhy4QeXF6sRyPMtBdLaS2m',
     'Sakura Customer', 'customer@example.com', '13800000001', 'CUSTOMER', 1),
    ('customer2', '$2a$10$ZU8cUUFJXEi7Bj7jvl0iCeh67WDH8A9Zhy4QeXF6sRyPMtBdLaS2m',
     'Haruto Sato', 'customer2@example.com', '13800000004', 'CUSTOMER', 1),
    ('frontdesk', '$2a$10$7ThQQ5JHUL0Rq9SM2OTX/OWtoDj1O/e0XO6TZEck9/dnyGTB7RFe2',
     'Sakura Front Desk', 'frontdesk@example.com', '13800000002', 'FRONT_DESK', 1),
    ('frontdesk2', '$2a$10$7ThQQ5JHUL0Rq9SM2OTX/OWtoDj1O/e0XO6TZEck9/dnyGTB7RFe2',
     'Aoi Tanaka', 'frontdesk2@example.com', '13800000005', 'FRONT_DESK', 1),
    ('admin', '$2a$10$UgjedYoDLSksLMIKPoaBpOkqAX0kCOSfLg3nKOq6XsqiSsZ6d7Cdq',
     'Sakura Admin', 'admin@example.com', '13800000003', 'ADMIN', 1)
ON DUPLICATE KEY UPDATE
    password = VALUES(password),
    name = VALUES(name),
    email = VALUES(email),
    phone = VALUES(phone),
    role = VALUES(role),
    status = VALUES(status);

-- -----------------------------------------------------------------------------
-- Hotels. The current schema has no city, description, or image_url columns;
-- location and hotel character are represented only by the existing address.
-- -----------------------------------------------------------------------------
INSERT INTO hotel (name, address, phone, check_in_time, check_out_time, status)
SELECT 'Sakura Inn', '2-8-12 Yanaka, Taito-ku, Tokyo', '03-5834-0101', '15:00:00', '11:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM hotel WHERE name = 'Sakura Inn');
UPDATE hotel SET address = '2-8-12 Yanaka, Taito-ku, Tokyo', phone = '03-5834-0101',
    check_in_time = '15:00:00', check_out_time = '11:00:00', status = 1
WHERE name = 'Sakura Inn';
SET @hotel_sakura := (SELECT id FROM hotel WHERE name = 'Sakura Inn' ORDER BY id LIMIT 1);

INSERT INTO hotel (name, address, phone, check_in_time, check_out_time, status)
SELECT 'Shinjuku City Hotel', '5-3-7 Nishi-Shinjuku, Shinjuku-ku, Tokyo', '03-5321-0202', '14:00:00', '11:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM hotel WHERE name = 'Shinjuku City Hotel');
UPDATE hotel SET address = '5-3-7 Nishi-Shinjuku, Shinjuku-ku, Tokyo', phone = '03-5321-0202',
    check_in_time = '14:00:00', check_out_time = '11:00:00', status = 1
WHERE name = 'Shinjuku City Hotel';
SET @hotel_shinjuku := (SELECT id FROM hotel WHERE name = 'Shinjuku City Hotel' ORDER BY id LIMIT 1);

INSERT INTO hotel (name, address, phone, check_in_time, check_out_time, status)
SELECT 'Asakusa Riverside Hotel', '1-16-4 Komagata, Taito-ku, Tokyo', '03-3842-0303', '15:00:00', '10:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM hotel WHERE name = 'Asakusa Riverside Hotel');
UPDATE hotel SET address = '1-16-4 Komagata, Taito-ku, Tokyo', phone = '03-3842-0303',
    check_in_time = '15:00:00', check_out_time = '10:00:00', status = 1
WHERE name = 'Asakusa Riverside Hotel';
SET @hotel_asakusa := (SELECT id FROM hotel WHERE name = 'Asakusa Riverside Hotel' ORDER BY id LIMIT 1);

INSERT INTO hotel (name, address, phone, check_in_time, check_out_time, status)
SELECT 'Yokohama Bay Hotel', '2-7-1 Shinko, Naka-ku, Yokohama', '045-212-0404', '15:00:00', '12:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM hotel WHERE name = 'Yokohama Bay Hotel');
UPDATE hotel SET address = '2-7-1 Shinko, Naka-ku, Yokohama', phone = '045-212-0404',
    check_in_time = '15:00:00', check_out_time = '12:00:00', status = 1
WHERE name = 'Yokohama Bay Hotel';
SET @hotel_yokohama := (SELECT id FROM hotel WHERE name = 'Yokohama Bay Hotel' ORDER BY id LIMIT 1);

INSERT INTO hotel (name, address, phone, check_in_time, check_out_time, status)
SELECT 'Osaka Namba Stay', '1-9-8 Namba, Chuo-ku, Osaka', '06-6645-0505', '14:00:00', '11:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM hotel WHERE name = 'Osaka Namba Stay');
UPDATE hotel SET address = '1-9-8 Namba, Chuo-ku, Osaka', phone = '06-6645-0505',
    check_in_time = '14:00:00', check_out_time = '11:00:00', status = 1
WHERE name = 'Osaka Namba Stay';
SET @hotel_osaka := (SELECT id FROM hotel WHERE name = 'Osaka Namba Stay' ORDER BY id LIMIT 1);

-- -----------------------------------------------------------------------------
-- Room types. total_rooms is synchronized from physical room rows below.
-- -----------------------------------------------------------------------------
INSERT INTO room_type (hotel_id, name, description, capacity, bed_type, base_price, total_rooms, status)
VALUES
    (@hotel_sakura, 'Single Room', 'Compact tatami-accented room for one guest.', 1, 'SINGLE', 380.00, 0, 1),
    (@hotel_sakura, 'Twin Room', 'Quiet twin room with a small tea corner.', 2, 'TWIN', 580.00, 0, 1),
    (@hotel_sakura, 'Deluxe Double', 'Spacious double room facing the inner garden.', 2, 'DOUBLE', 780.00, 0, 1),
    (@hotel_shinjuku, 'Single Room', 'Efficient business room with a work desk.', 1, 'SINGLE', 480.00, 0, 1),
    (@hotel_shinjuku, 'Double Room', 'City-view double room for short stays.', 2, 'DOUBLE', 680.00, 0, 1),
    (@hotel_shinjuku, 'Deluxe Double', 'High-floor double room with lounge seating.', 2, 'DOUBLE', 980.00, 0, 1),
    (@hotel_asakusa, 'Twin Room', 'Twin room convenient for temple sightseeing.', 2, 'TWIN', 580.00, 0, 1),
    (@hotel_asakusa, 'Family Room', 'Flexible family room with four sleeping places.', 4, 'TWIN_PLUS_FUTON', 880.00, 0, 1),
    (@hotel_asakusa, 'Suite', 'Riverside suite overlooking the Sumida River.', 3, 'KING_PLUS_SOFA', 1280.00, 0, 1),
    (@hotel_yokohama, 'Double Room', 'Modern double room near the harbor promenade.', 2, 'DOUBLE', 680.00, 0, 1),
    (@hotel_yokohama, 'Deluxe Double', 'Bay-view deluxe room on an upper floor.', 2, 'QUEEN', 980.00, 0, 1),
    (@hotel_yokohama, 'Suite', 'Corner suite with panoramic harbor views.', 4, 'KING_PLUS_SOFA', 1280.00, 0, 1),
    (@hotel_osaka, 'Single Room', 'Practical single room near Namba Station.', 1, 'SINGLE', 380.00, 0, 1),
    (@hotel_osaka, 'Twin Room', 'Casual twin room for city breaks.', 2, 'TWIN', 580.00, 0, 1),
    (@hotel_osaka, 'Family Room', 'Larger room for families and small groups.', 4, 'TWIN_PLUS_BUNK', 780.00, 0, 1)
ON DUPLICATE KEY UPDATE
    description = VALUES(description),
    capacity = VALUES(capacity),
    bed_type = VALUES(bed_type),
    base_price = VALUES(base_price),
    status = VALUES(status);

SET @rt_sakura_single := (SELECT id FROM room_type WHERE hotel_id = @hotel_sakura AND name = 'Single Room');
SET @rt_sakura_twin := (SELECT id FROM room_type WHERE hotel_id = @hotel_sakura AND name = 'Twin Room');
SET @rt_sakura_deluxe := (SELECT id FROM room_type WHERE hotel_id = @hotel_sakura AND name = 'Deluxe Double');
SET @rt_shinjuku_single := (SELECT id FROM room_type WHERE hotel_id = @hotel_shinjuku AND name = 'Single Room');
SET @rt_shinjuku_double := (SELECT id FROM room_type WHERE hotel_id = @hotel_shinjuku AND name = 'Double Room');
SET @rt_shinjuku_deluxe := (SELECT id FROM room_type WHERE hotel_id = @hotel_shinjuku AND name = 'Deluxe Double');
SET @rt_asakusa_twin := (SELECT id FROM room_type WHERE hotel_id = @hotel_asakusa AND name = 'Twin Room');
SET @rt_asakusa_family := (SELECT id FROM room_type WHERE hotel_id = @hotel_asakusa AND name = 'Family Room');
SET @rt_asakusa_suite := (SELECT id FROM room_type WHERE hotel_id = @hotel_asakusa AND name = 'Suite');
SET @rt_yokohama_double := (SELECT id FROM room_type WHERE hotel_id = @hotel_yokohama AND name = 'Double Room');
SET @rt_yokohama_deluxe := (SELECT id FROM room_type WHERE hotel_id = @hotel_yokohama AND name = 'Deluxe Double');
SET @rt_yokohama_suite := (SELECT id FROM room_type WHERE hotel_id = @hotel_yokohama AND name = 'Suite');
SET @rt_osaka_single := (SELECT id FROM room_type WHERE hotel_id = @hotel_osaka AND name = 'Single Room');
SET @rt_osaka_twin := (SELECT id FROM room_type WHERE hotel_id = @hotel_osaka AND name = 'Twin Room');
SET @rt_osaka_family := (SELECT id FROM room_type WHERE hotel_id = @hotel_osaka AND name = 'Family Room');

-- -----------------------------------------------------------------------------
-- Physical rooms: 12 rooms per hotel. One room per hotel is out of service.
-- -----------------------------------------------------------------------------
INSERT INTO room (hotel_id, room_type_id, room_number, floor, status)
VALUES
    (@hotel_sakura, @rt_sakura_single, '101', 1, 'AVAILABLE'),
    (@hotel_sakura, @rt_sakura_single, '102', 1, 'AVAILABLE'),
    (@hotel_sakura, @rt_sakura_single, '103', 1, 'AVAILABLE'),
    (@hotel_sakura, @rt_sakura_single, '104', 1, 'OUT_OF_SERVICE'),
    (@hotel_sakura, @rt_sakura_twin, '201', 2, 'AVAILABLE'),
    (@hotel_sakura, @rt_sakura_twin, '202', 2, 'AVAILABLE'),
    (@hotel_sakura, @rt_sakura_twin, '203', 2, 'AVAILABLE'),
    (@hotel_sakura, @rt_sakura_twin, '204', 2, 'AVAILABLE'),
    (@hotel_sakura, @rt_sakura_deluxe, '301', 3, 'AVAILABLE'),
    (@hotel_sakura, @rt_sakura_deluxe, '302', 3, 'AVAILABLE'),
    (@hotel_sakura, @rt_sakura_deluxe, '303', 3, 'AVAILABLE'),
    (@hotel_sakura, @rt_sakura_deluxe, '304', 3, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_single, '501', 5, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_single, '502', 5, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_single, '503', 5, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_single, '504', 5, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_double, '601', 6, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_double, '602', 6, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_double, '603', 6, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_double, '604', 6, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_deluxe, '701', 7, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_deluxe, '702', 7, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_deluxe, '703', 7, 'AVAILABLE'),
    (@hotel_shinjuku, @rt_shinjuku_deluxe, '704', 7, 'OUT_OF_SERVICE'),
    (@hotel_asakusa, @rt_asakusa_twin, '201', 2, 'AVAILABLE'),
    (@hotel_asakusa, @rt_asakusa_twin, '202', 2, 'AVAILABLE'),
    (@hotel_asakusa, @rt_asakusa_twin, '203', 2, 'AVAILABLE'),
    (@hotel_asakusa, @rt_asakusa_twin, '204', 2, 'AVAILABLE'),
    (@hotel_asakusa, @rt_asakusa_family, '301', 3, 'AVAILABLE'),
    (@hotel_asakusa, @rt_asakusa_family, '302', 3, 'AVAILABLE'),
    (@hotel_asakusa, @rt_asakusa_family, '303', 3, 'OUT_OF_SERVICE'),
    (@hotel_asakusa, @rt_asakusa_family, '304', 3, 'AVAILABLE'),
    (@hotel_asakusa, @rt_asakusa_suite, '401', 4, 'AVAILABLE'),
    (@hotel_asakusa, @rt_asakusa_suite, '402', 4, 'AVAILABLE'),
    (@hotel_asakusa, @rt_asakusa_suite, '403', 4, 'AVAILABLE'),
    (@hotel_asakusa, @rt_asakusa_suite, '404', 4, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_double, '801', 8, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_double, '802', 8, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_double, '803', 8, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_double, '804', 8, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_deluxe, '901', 9, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_deluxe, '902', 9, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_deluxe, '903', 9, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_deluxe, '904', 9, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_suite, '1001', 10, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_suite, '1002', 10, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_suite, '1003', 10, 'AVAILABLE'),
    (@hotel_yokohama, @rt_yokohama_suite, '1004', 10, 'OUT_OF_SERVICE'),
    (@hotel_osaka, @rt_osaka_single, '201', 2, 'AVAILABLE'),
    (@hotel_osaka, @rt_osaka_single, '202', 2, 'AVAILABLE'),
    (@hotel_osaka, @rt_osaka_single, '203', 2, 'AVAILABLE'),
    (@hotel_osaka, @rt_osaka_single, '204', 2, 'OUT_OF_SERVICE'),
    (@hotel_osaka, @rt_osaka_twin, '301', 3, 'AVAILABLE'),
    (@hotel_osaka, @rt_osaka_twin, '302', 3, 'AVAILABLE'),
    (@hotel_osaka, @rt_osaka_twin, '303', 3, 'AVAILABLE'),
    (@hotel_osaka, @rt_osaka_twin, '304', 3, 'AVAILABLE'),
    (@hotel_osaka, @rt_osaka_family, '401', 4, 'AVAILABLE'),
    (@hotel_osaka, @rt_osaka_family, '402', 4, 'AVAILABLE'),
    (@hotel_osaka, @rt_osaka_family, '403', 4, 'AVAILABLE'),
    (@hotel_osaka, @rt_osaka_family, '404', 4, 'AVAILABLE')
ON DUPLICATE KEY UPDATE
    room_type_id = VALUES(room_type_id),
    floor = VALUES(floor),
    status = VALUES(status);

UPDATE room_type rt
SET rt.total_rooms = (
    SELECT COUNT(*)
    FROM room r
    WHERE r.room_type_id = rt.id
)
WHERE rt.hotel_id IN (@hotel_sakura, @hotel_shinjuku, @hotel_asakusa, @hotel_yokohama, @hotel_osaka);

-- -----------------------------------------------------------------------------
-- Daily inventory. Most dates are open; deterministic sparse dates have one
-- reservation, one remaining sellable room, or are sold out for edge testing.
-- total_inventory always comes from the current physical-room count.
-- -----------------------------------------------------------------------------
INSERT INTO daily_inventory
    (room_type_id, stay_date, total_inventory, reserved_count, out_of_service_count)
SELECT
    inventory_seed.room_type_id,
    inventory_seed.stay_date,
    inventory_seed.total_inventory,
    CASE
        WHEN MOD(inventory_seed.day_offset + inventory_seed.pattern_key, 29) = 0
            THEN inventory_seed.total_inventory - inventory_seed.out_of_service_count
        WHEN MOD(inventory_seed.day_offset + inventory_seed.pattern_key, 13) = 0
            THEN GREATEST(inventory_seed.total_inventory - inventory_seed.out_of_service_count - 1, 0)
        WHEN MOD(inventory_seed.day_offset + inventory_seed.pattern_key, 7) = 0
            THEN LEAST(1, inventory_seed.total_inventory - inventory_seed.out_of_service_count)
        ELSE 0
    END AS reserved_count,
    inventory_seed.out_of_service_count
FROM (
    SELECT
        rt.id AS room_type_id,
        DATE_ADD('2026-09-22', INTERVAL days.day_offset DAY) AS stay_date,
        room_counts.total_inventory,
        room_counts.out_of_service_count,
        days.day_offset,
        MOD(rt.id, 11) AS pattern_key
    FROM room_type rt
    JOIN (
        SELECT
            r.room_type_id,
            COUNT(*) AS total_inventory,
            SUM(CASE WHEN r.status = 'OUT_OF_SERVICE' THEN 1 ELSE 0 END) AS out_of_service_count
        FROM room r
        GROUP BY r.room_type_id
    ) room_counts ON room_counts.room_type_id = rt.id
    CROSS JOIN (
        SELECT ones.n + tens.n * 10 AS day_offset
        FROM
            (SELECT 0 n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
             UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) ones
        CROSS JOIN
            (SELECT 0 n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
             UNION ALL SELECT 5) tens
    ) days
    WHERE rt.hotel_id IN (@hotel_sakura, @hotel_shinjuku, @hotel_asakusa, @hotel_yokohama, @hotel_osaka)
) inventory_seed
ON DUPLICATE KEY UPDATE
    total_inventory = VALUES(total_inventory),
    reserved_count = VALUES(reserved_count),
    out_of_service_count = VALUES(out_of_service_count);

-- -----------------------------------------------------------------------------
-- Verification queries
-- -----------------------------------------------------------------------------
SELECT COUNT(*) AS hotel_count FROM hotel;
SELECT COUNT(*) AS room_type_count FROM room_type;
SELECT COUNT(*) AS room_count FROM room;
SELECT COUNT(*) AS daily_inventory_count FROM daily_inventory;

SELECT COUNT(*) AS invalid_room_relationships
FROM room r
JOIN room_type rt ON rt.id = r.room_type_id
WHERE r.hotel_id <> rt.hotel_id;

SELECT COUNT(*) AS room_type_count_mismatches
FROM room_type rt
LEFT JOIN (
    SELECT room_type_id, COUNT(*) AS actual_room_count
    FROM room
    GROUP BY room_type_id
) rc ON rc.room_type_id = rt.id
WHERE rt.hotel_id IN (@hotel_sakura, @hotel_shinjuku, @hotel_asakusa, @hotel_yokohama, @hotel_osaka)
  AND rt.total_rooms <> COALESCE(rc.actual_room_count, 0);

SELECT COUNT(*) AS invalid_inventory_rows
FROM daily_inventory di
JOIN room_type rt ON rt.id = di.room_type_id
WHERE rt.hotel_id IN (@hotel_sakura, @hotel_shinjuku, @hotel_asakusa, @hotel_yokohama, @hotel_osaka)
  AND (
      di.total_inventory < 0
      OR di.reserved_count < 0
      OR di.out_of_service_count < 0
      OR di.reserved_count + di.out_of_service_count > di.total_inventory
      OR di.total_inventory <> (
          SELECT COUNT(*) FROM room r WHERE r.room_type_id = di.room_type_id
      )
  );
