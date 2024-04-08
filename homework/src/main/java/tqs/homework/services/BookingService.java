package tqs.homework.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import tqs.homework.data.Reservation;
import tqs.homework.data.Trip;

@Service
public class BookingService {
    public List<Trip> getTrips(){}

    public List<Trip> getTrips(Date tripDate,String fromLocation,String toLocation){}

    public Optional<Reservation> reserveSeat(int tripId,int seatNum){
        
    }
}
