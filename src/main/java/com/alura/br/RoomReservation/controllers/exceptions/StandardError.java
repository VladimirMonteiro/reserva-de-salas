package com.alura.br.RoomReservation.controllers.exceptions;

import java.time.Instant;
import java.util.Map;

public record StandardError(Instant timestamp, Integer status, String error, Map<String, String> fieldErrors, String path) {
}
