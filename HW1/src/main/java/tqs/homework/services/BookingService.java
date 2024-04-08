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
    public BookingService(AuthenticationService authService, TripRepository tripRepo, ReservationRepository resRepo,
            UserRepository userRepo) {
        this.authService = authService;
        this.tripRepo = tripRepo;
        this.resRepo = resRepo;
        this.userRepo = userRepo;
    }

    public List<Trip> getTrips() {
        return tripRepo.findAll();
    }

    public List<Trip> getTrips(Date tripDate, String fromLocation, String toLocation) {
        return tripRepo.findByTripDateAndFromLocationAndToLocation(tripDate, fromLocation, toLocation);
    }

    public Optional<Reservation> reserveSeat(String token, long tripId, int seatNum) {
        Optional<User> userToken = authService.getFromToken(token);
        Optional<Trip> trip = tripRepo.findById(tripId);
        Optional<Reservation> oldReservation = resRepo.findByTripIdAndSeat(tripId, seatNum);

        if (userToken.isPresent()
                && trip.isPresent()
                && seatNum > 0
                && seatNum <= trip.get().getNumSeats()
                && oldReservation.isEmpty()) {
            User user = userToken.get();
            Reservation reservation = new Reservation(user, trip.get(), seatNum);
            reservation = resRepo.save(reservation);
            user.addReservation(reservation);
            userRepo.save(user);
            return Optional.of(reservation);
        }
        return Optional.empty();
    }

    public void setAuthService(AuthenticationService authService){ //this is here because, when testing services, if I instantiate this class the normal way (@InjectMocks and spring/mockito handle the rest), this.authService will be null and manually instantiating it causes an error, so setting it afterwards is the best option for now
        this.authService=authService;
    }
}
