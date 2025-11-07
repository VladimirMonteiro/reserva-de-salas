package com.alura.br.RoomReservation.controllers.exceptions;

import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;
import com.alura.br.RoomReservation.services.exceptions.ReservationConflictException;
import com.alura.br.RoomReservation.services.exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> MethodArgumentNotValidException(MethodArgumentNotValidException e,
            HttpServletRequest request) {

        var status = HttpStatus.BAD_REQUEST.value();

        Map<String, String> fieldErrors = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(err -> err.getField(), err -> err.getDefaultMessage(), (msg1, msg2) -> msg1));

        StandardError error = new StandardError(
                Instant.now(),
                status,
                "Erro de validação",
                fieldErrors,
                request.getServletPath());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardError> UserAlreadyExistsException(UserAlreadyExistsException e,
            HttpServletRequest request) {

        var error = new StandardError(Instant.now(), HttpStatus.CONFLICT.value(), "Usuário inválido",
                Map.of("user", e.getMessage()), request.getServletPath());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> ObjectNotFoundException(ObjectNotFoundException e, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND.value();
        
        var error = new StandardError(Instant.now(), status, "Recurso não encontrado", Map.of("notFound", e.getMessage()), request.getServletPath());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<StandardError> ReservationConflictException (ReservationConflictException e, HttpServletRequest request) {
        var error = new StandardError(Instant.now(), HttpStatus.CONFLICT.value(),
                "Conflito de reserva", Map.of("reserva", e.getMessage()), request.getServletPath());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
