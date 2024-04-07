package tqs.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tqs.homework.data.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long>{
}
