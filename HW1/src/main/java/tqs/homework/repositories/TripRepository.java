package tqs.homework.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.homework.data.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip,Long> {
    public List<Trip> findByBusNumber(int busNumber);
    public List<Trip> findByTripDate(Date tripDate);
    public List<Trip> findByTripDateAndFromLocationAndToLocation(Date tripDate, String fromLocation, String toLocation);
}
