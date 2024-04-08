package tqs.homework.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.homework.data.Reservation;
import tqs.homework.data.Trip;
import tqs.homework.data.User;
import tqs.homework.repositories.ReservationRepository;
import tqs.homework.repositories.TripRepository;
import tqs.homework.repositories.UserRepository;

@Service
public class BookingService {

    private AuthenticationService authService;
    private TripRepository tripRepo;
    private ReservationRepository resRepo;
    private UserRepository userRepo;
    
    @Autowired
    public BookingService(AuthenticationService authService, TripRepository tripRepo, ReservationRepository resRepo, UserRepository userRepo){
        this.authService=authService;
        this.tripRepo=tripRepo;
        this.resRepo=resRepo;
        this.userRepo=userRepo;
    }

    public List<Trip> getTrips(){
        return tripRepo.findAll();
    }

    public List<Trip> getTrips(Date tripDate,String fromLocation,String toLocation){
        return tripRepo.findByTripDateAndFromLocationAndToLocation(tripDate, fromLocation, toLocation);
    }

    public Optional<Reservation> reserveSeat(String token,long tripId,int seatNum){
        Optional<User> userToken=authService.getFromToken(token);
        if(userToken.isEmpty()){
            return Optional.empty();
        }else{
            User user=userToken.get();
            Optional<Trip> trip=tripRepo.findById(tripId);
            if(trip.isEmpty() || seatNum>trip.get().getNumSeats()){
                return Optional.empty();
            }else{
                Reservation reservation=new Reservation(user, trip.get(), seatNum);
                resRepo.save(reservation);
                user.addReservation(reservation);
                userRepo.save(user);
                return Optional.of(reservation);
            }
        }
    }
}
