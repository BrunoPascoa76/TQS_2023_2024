package ua.tqs.bp.homework.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.tqs.bp.homework.data.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip,Long> {
    public List<Trip> findByBusNumber(String busNumber);
    public List<Trip> findByDay(Date day);
    public List<Trip> findByDayAndFromLocationAndToLocation(Date day, String fromLocation, String toLocation);
}
