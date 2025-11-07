package com.alura.br.RoomReservation.controllers;

import com.alura.br.RoomReservation.dto.reservation.CreateReservationRequestDto;
import com.alura.br.RoomReservation.dto.reservation.ReservationDto;
import com.alura.br.RoomReservation.dto.reservation.UpdateReservationRequestDto;
import com.alura.br.RoomReservation.services.IReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation (@RequestBody @Valid CreateReservationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> findById (@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.findAll(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation (@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody UpdateReservationRequestDto dto, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.updateReservation(dto, id));
    }
}
