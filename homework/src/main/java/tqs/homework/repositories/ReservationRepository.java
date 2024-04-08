package tqs.homework.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tqs.homework.data.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long>{
    Optional<Reservation> findByTripIdAndSeat(Long id, int seat);
}
