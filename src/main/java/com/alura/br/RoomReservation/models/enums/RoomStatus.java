package com.alura.br.RoomReservation.models.enums;

public enum RoomStatus {

    ACTIVE("ATIVO"),
    INACTIVE("INATIVO");

    private String description;

    RoomStatus (String description) {
        this.description = description;
    }
}
