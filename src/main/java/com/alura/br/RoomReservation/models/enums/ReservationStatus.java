package com.alura.br.RoomReservation.models.enums;

public enum ReservationStatus {
    ACTIVE("ATIVO"),
    CANCELED("CANCELADO"),
    NOT_RESERVED("NAO RESERVADO");

    private String description;

    ReservationStatus (String description) {
        this.description = description;
    }
}
