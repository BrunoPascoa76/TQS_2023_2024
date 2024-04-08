package tqs.homework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tqs.homework.data.Reservation;
import tqs.homework.data.Trip;
import tqs.homework.data.User;
import tqs.homework.services.AuthenticationService;
import tqs.homework.services.BookingService;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTests {

	private String token="3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0";

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper mapper;

	// authentication tests:
	@MockBean
	private AuthenticationService authService;

	private List<Trip> trips;
	private long dateInMillis;

	@BeforeAll
	void setup() throws ParseException {
		dateInMillis = new SimpleDateFormat("yyyy-MM-dd").parse("2024-04-06").getTime();
		Trip trip1 = new Trip(1, new Date(dateInMillis), new Time((21 * 3600 + 35 * 60) * 1000),
				new Time((22 * 3600 + 30 * 60) * 1000), "Porto", "Aveiro");
		Trip trip2 = new Trip(1, new Date(dateInMillis), new Time((19 * 3600 + 35 * 60) * 1000),
				new Time((20 * 3600 + 30 * 60) * 1000), "Porto", "Aveiro");
		Trip trip3 = new Trip(2, new Date(dateInMillis), new Time((12 * 3600 + 30 * 60) * 1000),
				new Time((13 * 3600 + 15 * 60) * 1000), "Aveiro", "Figueira da Foz");
		trips = Arrays.asList(trip1, trip2, trip3);
	}

	@Test
	@Order(1)
	void testLoginSuccessful() throws JsonProcessingException {
		User user = new User("Bruno", "password");
		user.setToken("3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0");
		when(authService.login(any(), any())).thenReturn(Optional.of(user));
		given()
				.mockMvc(mock)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mapper.writeValueAsString(user))
				.when()
				.post("/api/login")
				.then()
				.statusCode(200)
				.body("$",hasKey("token"));
	}

	@Test
	@Order(1)
	void testLoginWrongCredentials() throws JsonProcessingException {
		User user = new User("Rodrigo", "password");
		when(authService.login(any(), any())).thenReturn(Optional.empty());
		given()
				.mockMvc(mock)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mapper.writeValueAsString(user))
				.when()
				.post("/api/login")
				.then()
				.statusCode(401);
	}

	@Test
	@Order(1)
	void testRegisterSuccessful() throws JsonProcessingException, NoSuchAlgorithmException {
		User user = new User("João", "password");
		user.setToken("3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0");
		when(authService.register(any(), any())).thenReturn(Optional.of(user));
		given()
				.mockMvc(mock)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mapper.writeValueAsString(user))
				.when()
				.post("/api/register")
				.then()
				.statusCode(201)
				.body("$",hasKey("token"));
	}

	@Test
	@Order(1)
	void testRegisterUsernameTaken() throws JsonProcessingException, NoSuchAlgorithmException {
		User user = new User("Bruno", "something");
		when(authService.register(any(), any())).thenReturn(Optional.empty());
		given()
				.mockMvc(mock)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mapper.writeValueAsString(user))
				.when()
				.post("/api/register")
				.then()
				.statusCode(409);
	}
	// booking tests:
	@MockBean
	private BookingService bookingService;

	@Test
	@Order(2)
	void listAllTrips() {
		when(bookingService.getTrips()).thenReturn(trips);
		given()
				.mockMvc(mock)
				.when()
				.get("/api/trips/list")
				.then()
				.statusCode(200)
				.body("occupiedSeats", hasSize(3)); // testing correct processing of the objects before sending
	}

	@Test
	@Order(2)
	void listTripsParameters() {
		when(bookingService.getTrips(any(), any(), any())).thenReturn(Arrays.asList(trips.get(0), trips.get(1)));
		given()
				.mockMvc(mock)
				.param("tripDate", dateInMillis)
				.param("fromLocation","Porto")
				.param("toLocation","Aveiro")
				.when()
				.get("/api/trips")
				.then()
				.statusCode(200)
				.body("occupiedSeats", hasSize(2));
	}

	@Test
	@Order(2)
	void listTripsParametersEmpty() {
		when(bookingService.getTrips(any(), any(), any())).thenReturn(new ArrayList<>());
		given()
				.mockMvc(mock)
				.param("tripDate", dateInMillis)
				.param("fromLocation", "Porto")
				.param("toLocation", "Aveiro")
				.when()
				.get("/api/trips")
				.then()
				.statusCode(200)
				.body("occupiedSeats", hasSize(0));
	}

	@Test
	@Order(2)
	void scheduleTripSuccess() throws JsonProcessingException {
		Reservation schedule = new Reservation(
				new User("Rodrigo", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"), trips.get(0),
				12);
		when(bookingService.reserveSeat(any(),anyLong(), anyInt())).thenReturn(Optional.of(schedule));

		given()
				.mockMvc(mock)
				.header("token",token)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mapper.writeValueAsString(schedule))
				.when()
				.post("/api/trips/schedule")
				.then()
				.statusCode(201);
	}

	@Test
	@Order(2)
	void scheduleTripSeatOccupied() throws JsonProcessingException{
		when(bookingService.reserveSeat(any(),anyLong(), anyInt())).thenReturn(Optional.empty());
		Reservation schedule = new Reservation(
				new User("Rodrigo", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"), trips.get(0),
				11);

		given()
				.mockMvc(mock)
				.header("token",token)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mapper.writeValueAsString(schedule))
				.when()
				.post("/api/trips/schedule")
				.then()
				.statusCode(403);
	}

	@Test
	@Order(2)
	void scheduleTripNotLoggedIn() throws JsonProcessingException{
		//the code is the same as the successful one (except for the token) on purpose, to show that, even if everything's correct, it won't work unless you're logged in
		Reservation schedule = new Reservation(
				new User("Rodrigo", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"), trips.get(0),
				12);
		when(bookingService.reserveSeat(any(), anyLong(),anyInt())).thenReturn(Optional.of(schedule));

		given()
				.mockMvc(mock)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mapper.writeValueAsString(schedule))
				.when()
				.post("/api/trips/schedule")
				.then()
				.statusCode(400);

	}
}
