CREATE DATABASE IF NOT EXISTS rs01
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE rs01;

CREATE TABLE IF NOT EXISTS `user` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NULL,
    email VARCHAR(100) NULL,
    phone VARCHAR(30) NULL,
    role VARCHAR(30) NOT NULL,
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_username (username),
    KEY idx_user_phone (phone),
    KEY idx_user_role_status (role, status),
    CONSTRAINT chk_user_role CHECK (role IN ('CUSTOMER', 'FRONT_DESK', 'ADMIN'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS hotel (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(500) NOT NULL,
    phone VARCHAR(32) NOT NULL,
    check_in_time TIME NOT NULL,
    check_out_time TIME NOT NULL,
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_hotel_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS room_type (
    id BIGINT NOT NULL AUTO_INCREMENT,
    hotel_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000) NULL,
    capacity INT NOT NULL,
    bed_type VARCHAR(64) NOT NULL,
    base_price DECIMAL(10, 2) NOT NULL,
    total_rooms INT NOT NULL,
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_room_type_hotel_name (hotel_id, name),
    KEY idx_room_type_hotel_status (hotel_id, status),
    CONSTRAINT fk_room_type_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS room (
    id BIGINT NOT NULL AUTO_INCREMENT,
    hotel_id BIGINT NOT NULL,
    room_type_id BIGINT NOT NULL,
    room_number VARCHAR(32) NOT NULL,
    floor INT NOT NULL,
    status VARCHAR(32) NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_room_hotel_number (hotel_id, room_number),
    KEY idx_room_type_status (room_type_id, status),
    CONSTRAINT fk_room_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id),
    CONSTRAINT fk_room_room_type FOREIGN KEY (room_type_id) REFERENCES room_type (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS daily_inventory (
    id BIGINT NOT NULL AUTO_INCREMENT,
    room_type_id BIGINT NOT NULL,
    stay_date DATE NOT NULL,
    total_inventory INT NOT NULL,
    reserved_count INT NOT NULL DEFAULT 0,
    out_of_service_count INT NOT NULL DEFAULT 0,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_daily_inventory_room_type_date (room_type_id, stay_date),
    KEY idx_daily_inventory_stay_date (stay_date),
    CONSTRAINT fk_daily_inventory_room_type FOREIGN KEY (room_type_id) REFERENCES room_type (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS reservation (
    id BIGINT NOT NULL AUTO_INCREMENT,
    reservation_no VARCHAR(64) NOT NULL,
    user_id BIGINT NOT NULL,
    hotel_id BIGINT NOT NULL,
    room_type_id BIGINT NOT NULL,
    assigned_room_id BIGINT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    guest_name VARCHAR(100) NOT NULL,
    guest_phone VARCHAR(30) NULL,
    guest_count INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    actual_check_in_time DATETIME NULL,
    actual_check_out_time DATETIME NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_reservation_no (reservation_no),
    KEY idx_reservation_user_created (user_id, create_time),
    KEY idx_reservation_hotel_dates (hotel_id, check_in_date, check_out_date),
    KEY idx_reservation_room_type_dates (room_type_id, check_in_date, check_out_date),
    KEY idx_reservation_status (status),
    CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES `user` (id),
    CONSTRAINT fk_reservation_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id),
    CONSTRAINT fk_reservation_room_type FOREIGN KEY (room_type_id) REFERENCES room_type (id),
    CONSTRAINT fk_reservation_assigned_room FOREIGN KEY (assigned_room_id) REFERENCES room (id),
    CONSTRAINT chk_reservation_status CHECK (
        status IN ('CONFIRMED', 'CHECKED_IN', 'CHECKED_OUT', 'CANCELLED', 'NO_SHOW')
    )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS reservation_night (
    id BIGINT NOT NULL AUTO_INCREMENT,
    reservation_id BIGINT NOT NULL,
    stay_date DATE NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_reservation_night_reservation_date (reservation_id, stay_date),
    KEY idx_reservation_night_stay_date (stay_date),
    CONSTRAINT fk_reservation_night_reservation FOREIGN KEY (reservation_id)
        REFERENCES reservation (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
