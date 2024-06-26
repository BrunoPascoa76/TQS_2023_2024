package tqs.homework.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.homework.data.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip,Long> {
    @Cacheable("trips")
    public List<Trip> findByTripDateAndFromLocationAndToLocation(Date tripDate, String fromLocation, String toLocation);
}
