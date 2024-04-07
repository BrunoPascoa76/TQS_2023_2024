package ua.tqs.bp.homework;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Testcontainers
public class BookingTestIT {
    @LocalServerPort
    int port;

    private JSONObject requestBody;
    private Response response;

    @Container
    private static MySQLContainer<?> container = new MySQLContainer<>("mysql:latest")
            .withUsername("sa")
            .withPassword("password")
            .withDatabaseName("testDB");

    @DynamicPropertySource
    public void setupContainer(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @When("I try to list all trips")
    public void listAllTrips(){
        response=RestAssured
        .given()
        .when()
            .get("/api/trips/list");
    }

    @When("I try to list all trips between {string} and {string} on {string}")
    public void listParameters(String fromLocation,String toLocation, String dateString) throws ParseException{
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        
        response=RestAssured
            .given()
            .param("fromLocation",fromLocation)
            .param("toLocation",toLocation)
            .param("date",date)
            .when()
            .get("/api/trip");
    }

    @When("I choose the trip with the ID {int}")
    public void bookTripId(int id){
        requestBody=new JSONObject();
        requestBody.put("id",(long)id);
    }

    @When("I choose seat number {int}")
    public void bookTripSeat(int seat){
        requestBody.put("seat",seat);
        response=RestAssured
        .given()
        .contentType("application/json")
        .body(requestBody.toString())
        .when()
        .post("/api/trip");
    }

    @Then("I get {int} results")
    public void checkNumberResults(int expected){
        response.then().statusCode(200).body("id",hasSize(expected));
    }


}
