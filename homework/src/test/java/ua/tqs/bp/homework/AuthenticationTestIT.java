package ua.tqs.bp.homework;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.json.JSONException;
import org.json.JSONObject;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public class AuthenticationTestIT {
    @LocalServerPort
    int port;

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

    @Given("a working server connection")
    public void setupRestAssured() {
        RestAssured.port = port;
    }

    @When("I try to login with the username {string} and the password {string}")
    public void loginAttempt(String username, String password) throws JSONException {
        String requestBody=new JSONObject().put("username",username).put("password",password).toString();
        
        response = RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/login");
    }

    @When("I try to register with the username {string} and the password {string}")
    public void registerAttempt(String username, String password) throws JSONException {
        String requestBody=new JSONObject().put("username",username).put("password",password).toString();

        response = RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/register");
    }

    @When("I try to logout")
    public void logout(){
        response=RestAssured
            .given()
            .when()
            .delete("/api/logout");
    }

    @Then("it returns me a status {int} message")
    public void verifyStatusCode(int code){
        response.then().statusCode(code);
    }

}
