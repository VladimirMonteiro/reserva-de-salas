package com.alura.br.RoomReservation.services.exceptions;

public class ReservationConflictException extends RuntimeException {
    public ReservationConflictException (String message) {
        super(message);
    }
}
