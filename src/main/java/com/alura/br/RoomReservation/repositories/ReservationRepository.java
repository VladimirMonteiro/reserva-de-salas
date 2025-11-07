package com.alura.br.RoomReservation.repositories;

import com.alura.br.RoomReservation.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
        FROM Reservation r
        WHERE r.room.id = :roomId
          AND r.reservationStatus <> 'CANCELLED'
          AND (
                (r.initialDate < :endDate) AND
                (r.endDate > :startDate)
              )
    """)
    boolean existsConflict(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
