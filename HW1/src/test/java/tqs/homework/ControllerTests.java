package tqs.homework;

import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
	}

	@Test
	@Order(1)
	void testLoginSuccessful() throws JsonProcessingException {
		User user = new User("Bruno", "password");
		user.setToken("3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0");
		when(authService.login("Bruno", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8")).thenReturn(Optional.of(user));
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
	void testLoginWrongUsername() throws JsonProcessingException {
		User user = new User("Rodrigo", "password");
		when(authService.login("Rodrigo", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8")).thenReturn(Optional.empty());
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
	void testLoginWrongPassword() throws JsonProcessingException {
		User user = new User("Bruno", "wrongPassword");
		when(authService.login("Rodrigo", "4aecb7f750b48cd608b09851345fe9a82405a46751acc0e5fc1b88db3610260b")).thenReturn(Optional.empty());
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
		when(authService.register("João","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8")).thenReturn(Optional.of(user));
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
		when(authService.register("Bruno","3fc9b689459d738f8c88a3a48aa9e33542016b7a4052e001aaa536fca74813cb")).thenReturn(Optional.empty());
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
		when(bookingService.getTrips(new Date(dateInMillis), "Porto","Aveiro")).thenReturn(Arrays.asList(trips.get(0), trips.get(1)));
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
		when(bookingService.getTrips(new Date(dateInMillis),"Porto","Coimbra")).thenReturn(new ArrayList<>());
		given()
				.mockMvc(mock)
				.param("tripDate", dateInMillis)
				.param("fromLocation", "Porto")
				.param("toLocation", "Coimbra")
				.when()
				.get("/api/trips")
				.then()
				.statusCode(200)
				.body("occupiedSeats", hasSize(0));
	}

	@Test
	@Order(2)
	void scheduleTripSuccess() throws JsonProcessingException {
		trips.get(0).setId(1L);
		Reservation schedule = new Reservation(
				new User("Rodrigo", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"), trips.get(0),
				12);
		when(bookingService.reserveSeat(token,1L, 12)).thenReturn(Optional.of(schedule));

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
		trips.get(0).setId(1L);
		when(bookingService.reserveSeat(token,1L, 11)).thenReturn(Optional.empty());
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
		trips.get(0).setId(1L);
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

	//web Controller tests:
	@Test
	@Order(3)
	void checkHomeLoggedIn() throws Exception{
		User user = new User("Bruno", "password");
		when(authService.getFromToken(token)).thenReturn(Optional.of(user));
		mock.perform(get("/").header("token", token))
			.andExpect(status().isOk())
			.andExpect(model().attribute("username",equalTo("Bruno")))
			.andExpect(model().attribute("locations",hasSize(3)))
			.andExpect(content().contentType("text/html;charset=UTF-8"));
	}

	@Test
	@Order(3)
	void checkHomeNotLoggedIn() throws Exception{
		mock.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("username",is(nullValue())))
			.andExpect(model().attribute("locations",hasSize(3)))
			.andExpect(content().contentType("text/html;charset=UTF-8"));
	}

	@Test
	@Order(3)
	void checkResultsLoggedIn() throws Exception{
		User user = new User("Bruno", "password");
		when(authService.getFromToken(token)).thenReturn(Optional.of(user));
		when(bookingService.getTrips(new Date(1712361600000L), "Porto", "Aveiro")).thenReturn(Arrays.asList(trips.get(0),trips.get(1)));
		mock.perform(get("/results?tripDate=1712361600000&fromLocation=Porto&toLocation=Aveiro").header("token", token))
			.andExpect(status().isOk())
			.andExpect(model().attribute("username",equalTo("Bruno")))
			.andExpect(model().attribute("fromLocation", equalTo("Porto")))
			.andExpect(model().attribute("toLocation", equalTo("Aveiro")))
			.andExpect(model().attribute("trips", hasSize(2)))
			.andExpect(content().contentType("text/html;charset=UTF-8"));
	}

	@Test
	@Order(3)
	void checkResultsNotLoggedIn() throws Exception{
		when(bookingService.getTrips(new Date(1712361600000L), "Porto", "Aveiro")).thenReturn(Arrays.asList(trips.get(0),trips.get(1)));
		mock.perform(get("/results?tripDate=1712361600000&fromLocation=Porto&toLocation=Aveiro"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("username",is(nullValue())))
			.andExpect(model().attribute("fromLocation", equalTo("Porto")))
			.andExpect(model().attribute("toLocation", equalTo("Aveiro")))
			.andExpect(model().attribute("trips", hasSize(2)))
			.andExpect(content().contentType("text/html;charset=UTF-8"));
	}
	@Test
	@Order(3)
	void checkLogin() throws Exception{
		mock.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/html;charset=UTF-8"));
	}

	@Test
	@Order(3)
	void checkRegister() throws Exception{
		mock.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/html;charset=UTF-8"));
	}
}
