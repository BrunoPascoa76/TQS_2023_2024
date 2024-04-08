package tqs.homework;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import tqs.homework.data.Reservation;
import tqs.homework.data.Trip;
import tqs.homework.data.User;
import tqs.homework.repositories.ReservationRepository;
import tqs.homework.repositories.TripRepository;
import tqs.homework.repositories.UserRepository;
import tqs.homework.services.AuthenticationService;
import tqs.homework.services.BookingService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {
    @Mock
    private UserRepository userRepo;
    @Mock
    private ReservationRepository resRepo;
    @Mock
    private TripRepository tripRepo;

    private long dateInMillis;
    private List<Trip> trips;
    private String token = "3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0";
    private User bruno;
    private User joao;
    private Reservation oldReservation;
    private Reservation newReservation;


    @InjectMocks
    private AuthenticationService authService;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setup() throws ParseException {
        bookingService.setAuthService(authService);
        dateInMillis = new SimpleDateFormat("yyyy-MM-dd").parse("2024-04-06").getTime();
        Trip trip1 = new Trip(1, new Date(dateInMillis), new Time((21 * 3600 + 35 * 60) * 1000),
                new Time((22 * 3600 + 30 * 60) * 1000), "Porto", "Aveiro");
        Trip trip2 = new Trip(1, new Date(dateInMillis), new Time((19 * 3600 + 35 * 60) * 1000),
                new Time((20 * 3600 + 30 * 60) * 1000), "Porto", "Aveiro");
        Trip trip3 = new Trip(2, new Date(dateInMillis), new Time((12 * 3600 + 30 * 60) * 1000),
                new Time((13 * 3600 + 15 * 60) * 1000), "Aveiro", "Figueira da Foz");
        trips = Arrays.asList(trip1, trip2, trip3);

        trip1.setId(1L);

        // already exists
        bruno = new User("Bruno", "password");
        bruno.setToken(token);
        // will register
        joao = new User("João", "password");

        oldReservation = new Reservation(bruno, trip1, 11);
        newReservation = new Reservation(bruno, trip1, 12);

    }

    // authentication tests:
    @Test
    void testLoginSuccessful() {
        when(userRepo.findByUsernameAndPassword("Bruno", "password")).thenReturn(Optional.of(bruno));
        assertEquals(bruno, authService.login("Bruno", "password").get());
    }

    @Test
    void testLoginWrongUsername() {
        when(userRepo.findByUsernameAndPassword("Rodrigo", "password")).thenReturn(Optional.empty());
        assertTrue(authService.login("Rodrigo", "password").isEmpty());
    }

    @Test
    void testLoginWrongPassword() {
        when(userRepo.findByUsernameAndPassword("Bruno", "wrongPassword")).thenReturn(Optional.empty());
        assertTrue(authService.login("Bruno", "wrongPassword").isEmpty());
    }

    @Test
    void testRegisterSuccessful() throws NoSuchAlgorithmException {
        when(userRepo.save(any())).thenReturn(joao);
        when(userRepo.findByUsername("joao")).thenReturn(Optional.empty());
        assertEquals(joao, authService.register("joao", "password").get());
    }

    @Test
    void testRegisterUsernameTaken() throws NoSuchAlgorithmException {
        // when(userRepo.save(any())).thenReturn(joao);
        when(userRepo.findByUsername("Bruno")).thenReturn(Optional.of(bruno));
        assertTrue(authService.register("Bruno", "otherPassword").isEmpty());
    }

    @Test
    void testGetFromToken() {
        when(userRepo.findByToken(token)).thenReturn(Optional.of(bruno));
        assertEquals(bruno, authService.getFromToken(token).get());
    }

    // booking service:
    @Test
    void testGetAllTrips() {
        when(tripRepo.findAll()).thenReturn(trips);
        assertEquals(trips, bookingService.getTrips());
    }

    @Test
    void testGetTripsByParameters() {
        List<Trip> validTrips = Arrays.asList(trips.get(0), trips.get(1));
        when(tripRepo.findByTripDateAndFromLocationAndToLocation(new Date(dateInMillis), "Porto", "Aveiro"))
                .thenReturn(validTrips);
        assertEquals(validTrips, bookingService.getTrips(new Date(dateInMillis), "Porto", "Aveiro"));
    }

    @Test
    void testReserveSuccessful() {
        when(authService.getFromToken(token)).thenReturn(Optional.of(bruno));
        when(tripRepo.findById(1L)).thenReturn(Optional.of(trips.get(0)));
        when(resRepo.findByTripIdAndSeat(1L, 12)).thenReturn(Optional.empty());
        when(userRepo.save(any())).thenReturn(bruno);
        when(resRepo.save(any())).thenReturn(newReservation);
        assertEquals(newReservation, bookingService.reserveSeat(token,1L,12).get());
    }

    @Test
    void testReserveWrongToken(){
        when(authService.getFromToken("wrongToken")).thenReturn(Optional.empty());
        when(tripRepo.findById(1L)).thenReturn(Optional.of(trips.get(0)));
        when(resRepo.findByTripIdAndSeat(1L, 12)).thenReturn(Optional.empty());
        assertTrue(bookingService.reserveSeat("wrongToken", 1L, 12).isEmpty());
    }

    @Test
    void testReserveWrongTripId(){
        when(authService.getFromToken(token)).thenReturn(Optional.of(bruno));
        when(tripRepo.findById(99L)).thenReturn(Optional.empty());
        when(resRepo.findByTripIdAndSeat(99L, 12)).thenReturn(Optional.empty());
        assertTrue(bookingService.reserveSeat(token, 99L, 12).isEmpty());
    }

    @Test
    void testReserveSeatTooSmall(){
        when(authService.getFromToken(token)).thenReturn(Optional.of(bruno));
        when(tripRepo.findById(1L)).thenReturn(Optional.of(trips.get(0)));
        when(resRepo.findByTripIdAndSeat(1L, -1)).thenReturn(Optional.empty());
        assertTrue(bookingService.reserveSeat(token, 1L, -1).isEmpty());
    }

    @Test
    void testReserveSeatTooBig(){
        when(authService.getFromToken("wrongToken")).thenReturn(Optional.of(bruno));
        when(tripRepo.findById(1L)).thenReturn(Optional.of(trips.get(0)));
        when(resRepo.findByTripIdAndSeat(1L, 99)).thenReturn(Optional.empty());
        assertTrue(bookingService.reserveSeat("wrongToken", 1L, 99).isEmpty());
    }
    
}
