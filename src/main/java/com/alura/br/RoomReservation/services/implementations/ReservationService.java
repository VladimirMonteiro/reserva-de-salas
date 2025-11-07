package com.alura.br.RoomReservation.services.implementations;

import com.alura.br.RoomReservation.dto.reservation.CreateReservationRequestDto;
import com.alura.br.RoomReservation.dto.reservation.ReservationDto;
import com.alura.br.RoomReservation.dto.reservation.UpdateReservationRequestDto;
import com.alura.br.RoomReservation.models.Reservation;
import com.alura.br.RoomReservation.models.enums.RoomStatus;
import com.alura.br.RoomReservation.repositories.ReservationRepository;
import com.alura.br.RoomReservation.repositories.RoomRepository;
import com.alura.br.RoomReservation.repositories.UserRepository;
import com.alura.br.RoomReservation.services.IReservationService;
import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;
import com.alura.br.RoomReservation.services.exceptions.ReservationConflictException;
import com.alura.br.RoomReservation.utils.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Transactional(readOnly = true)
    public ReservationDto findById (Long id) {
        var reservationIfExists = reservationRepository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException("Reserva não encontrada."));

        return ReservationMapper.toDto(reservationIfExists);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDto> findAll (int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reservation> pageReservation = reservationRepository.findAll(pageable);
        return pageReservation.getContent().stream().map(ReservationMapper::toDto).toList();
    }

    @Override
    public void deleteReservation (Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ObjectNotFoundException("Reserva não encontrada.");
        }
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ReservationDto updateReservation (UpdateReservationRequestDto dto, Long id) {
        var reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Reserva não encontrada."));

        boolean conflict = reservationRepository.existsConflictExcludingCurrent(
                reservation.getRoom().getId(),
                reservation.getId(),
                dto.initialDate(),
                dto.endDate()
        );

        if (conflict) {
            throw new ReservationConflictException("A sala já está reservada neste período.");
        }

        var updated = ReservationMapper.updateReservationToEntity(dto, reservation);
        reservationRepository.save(updated);

        return ReservationMapper.toDto(updated);
    }
}
