package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.reservation.ReservationDto;
import com.alura.br.RoomReservation.dto.room.RoomSummaryDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.Reservation;
import com.alura.br.RoomReservation.models.Room;
import com.alura.br.RoomReservation.models.User;
import com.alura.br.RoomReservation.models.enums.ReservationStatus;
import com.alura.br.RoomReservation.models.enums.RoomStatus;
import com.alura.br.RoomReservation.repositories.ReservationRepository;
import com.alura.br.RoomReservation.repositories.RoomRepository;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private Reservation reservation;

    private UserDto userDto;
    private ReservationDto reservationDto;
    private RoomSummaryDto roomDto;

    @BeforeEach
    void setUp () {

        var user = new User(1L, "Ana", "253.407.490-37", 30, "(51) 99999-9999", "ana@email.com", null);
        var room = new Room(1L, "Sala reunião", 30, RoomStatus.ACTIVE, null);

        reservation = new Reservation(1L,
                LocalDate.of(2025, 11, 6),
                LocalDate.of(2025, 11, 7),
                ReservationStatus.ACTIVE,
                room,
                user);
        userDto = buildUserDto();
        roomDto = buildRoomDto();
        reservationDto = buildReservationDto();
    }


    @Test
    void createReservation () {
    }

    @Test
    void shouldReturnReservationDtoWhenIdIsValid () {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        var result = reservationService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Ana", result.userDto().name());
        assertEquals("Sala reunião", result.roomDto().name());
    }

    @Test
    void shouldThrowObjectNotFoundWhenReservationNotFound () {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = assertThrows(ObjectNotFoundException.class, () -> reservationService.findById(1L));

        assertEquals("Reserva não encontrada.", exception.getMessage());
    }

    @Test
    void shouldReturnPageOfListReservations () {
        var listReservations = List.of(reservation);
        Page<Reservation> reservationsPage = new PageImpl<>(listReservations);

        when(reservationRepository.findAll(any(Pageable.class))).thenReturn(reservationsPage);

        var result = reservationService.findAll(0, 20);

        assertNotNull(result);
        assertEquals(reservation.getId(), result.getFirst().id());
        assertEquals(reservation.getInitialDate(), result.getFirst().initialDate());
        assertEquals(reservation.getEndDate(), result.getFirst().endDate());
        assertEquals(reservation.getUser().getId(), result.getFirst().userDto().id());
        assertEquals(reservation.getRoom().getId(), result.getFirst().roomDto().id());
    }


    private ReservationDto buildReservationDto () {
        return new ReservationDto(1L,
                LocalDate.of(2025, 11, 6),
                LocalDate.of(2025, 11, 7),
                ReservationStatus.ACTIVE,
                userDto,
                roomDto);
    }

    private UserDto buildUserDto () {
        return new UserDto(1L, "Ana",
                "253.407.490-37",
                30,
                "(51) 99999-9999",
                "ana@email.com");
    }

    private RoomSummaryDto buildRoomDto () {
        return new RoomSummaryDto(1L, "Sala reunião", 30, RoomStatus.ACTIVE);
    }
}