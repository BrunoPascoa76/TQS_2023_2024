package ua.tqs.bp.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.tqs.bp.homework.data.Reservation;

public interface ReservationRepository extends JpaRepository<Long,Reservation>{
}
