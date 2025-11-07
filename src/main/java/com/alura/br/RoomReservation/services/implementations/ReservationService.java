package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.reservation.CreateReservationRequestDto;
import com.alura.br.RoomReservation.dto.reservation.ReservationDto;
import com.alura.br.RoomReservation.models.enums.RoomStatus;
import com.alura.br.RoomReservation.repositories.ReservationRepository;
import com.alura.br.RoomReservation.repositories.RoomRepository;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.IReservationService;
import com.alura.br.RoomReservation.services.exceptions.ReservationConflictException;
import com.alura.br.RoomReservation.utils.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public ReservationDto createReservation (CreateReservationRequestDto dto) {
        var room = roomRepository.getReferenceById(dto.roomId());
        var user = userRepository.getReferenceById(dto.userId());

        boolean reservationConflict = reservationRepository.existsConflict(dto.roomId(), dto.initialDate(), dto.endDate());

        if (reservationConflict) {
            throw new ReservationConflictException("A sala já está reservada neste período.");
        }

        if (room.getRoomStatus().equals(RoomStatus.INACTIVE)) {
            throw new ReservationConflictException("Sala inativa.");
        }

        var newReservation = ReservationMapper.toEntity(dto);
        newReservation.setRoom(room);
        newReservation.setUser(user);

        var savedReservation = reservationRepository.save(newReservation);
        return ReservationMapper.toDto(savedReservation);
    }
}
