package com.rs01.reservation.service;

import com.rs01.reservation.dto.ReservationCreateDTO;
import com.rs01.reservation.vo.ReservationDetailVO;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    public ReservationDetailVO createReservation(ReservationCreateDTO request) {
        // TODO: core business logic should be implemented manually by developer
        throw unsupported();
    }

    public void cancelReservation(Long reservationId) {
        // TODO: core business logic should be implemented manually by developer
        throw unsupported();
    }

    public void checkIn(Long reservationId, Long roomId) {
        // TODO: core business logic should be implemented manually by developer
        throw unsupported();
    }

    public void checkOut(Long reservationId) {
        // TODO: core business logic should be implemented manually by developer
        throw unsupported();
    }

    public void markNoShow(Long reservationId) {
        // TODO: core business logic should be implemented manually by developer
        throw unsupported();
    }

    private UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("Core reservation logic has not been implemented");
    }
}
