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
    private String token="3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0";
    private User bruno;
    private User joao;

    @InjectMocks
    private AuthenticationService authService;
    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
	void setup() throws ParseException {
		dateInMillis = new SimpleDateFormat("yyyy-MM-dd").parse("2024-04-06").getTime();
		Trip trip1 = new Trip(1, new Date(dateInMillis), new Time((21 * 3600 + 35 * 60) * 1000),
				new Time((22 * 3600 + 30 * 60) * 1000), "Porto", "Aveiro");
		Trip trip2 = new Trip(1, new Date(dateInMillis), new Time((19 * 3600 + 35 * 60) * 1000),
				new Time((20 * 3600 + 30 * 60) * 1000), "Porto", "Aveiro");
		Trip trip3 = new Trip(2, new Date(dateInMillis), new Time((12 * 3600 + 30 * 60) * 1000),
				new Time((13 * 3600 + 15 * 60) * 1000), "Aveiro", "Figueira da Foz");
		trips = Arrays.asList(trip1, trip2, trip3);

        //already exists
        bruno=new User("Bruno", "password");
        bruno.setToken(token);
		//will register
        joao= new User("João", "password");
	}

    //authentication tests:
    @Test
    void testLoginSuccessful(){
        when(userRepo.findByUsernameAndPassword(any(), any())).thenReturn(Optional.of(bruno));
        assertEquals(bruno,authService.login("Bruno","password").get());
    }

    @Test
    void testLoginWrongUsername(){
        when(userRepo.findByUsernameAndPassword(any(), any())).thenReturn(Optional.empty());
        assertTrue(authService.login("Rodrigo", "password").isEmpty());
    }

    @Test
    void testLoginWrongPassword(){
        when(userRepo.findByUsernameAndPassword(any(), any())).thenReturn(Optional.empty());
        assertTrue(authService.login("Bruno","wrongPassword").isEmpty());
    }

    @Test
    void testRegisterSuccessful() throws NoSuchAlgorithmException{
        when(userRepo.save(any())).thenReturn(joao);
        when(userRepo.findByUsername(any())).thenReturn(Optional.empty());
        assertEquals(joao,authService.register("joao", "password").get());
    }

    @Test
    void testRegisterUsernameTaken() throws NoSuchAlgorithmException {
        //when(userRepo.save(any())).thenReturn(joao);
        when(userRepo.findByUsername(any())).thenReturn(Optional.of(bruno));
        assertTrue(authService.register("Bruno", "otherPassword").isEmpty());
    }
}
