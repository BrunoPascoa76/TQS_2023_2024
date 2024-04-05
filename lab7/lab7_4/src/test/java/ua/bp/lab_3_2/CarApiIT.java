package ua.bp.lab_3_2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.jboss.jandex.ParameterizedType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;

import static org.hamcrest.Matchers.*;

import ua.bp.lab_3_2.data.Car;
import ua.bp.lab_3_2.data.CarRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class CarApiIT {
    @LocalServerPort
    int port;

    @BeforeEach
    public void setup(){
        RestAssured.port=port;
    }

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("cars");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CarRepository repo;

    @Test
    @Order(1)
    void whenValidCar_thenCreateCar() throws Exception {
        Car car = new Car("Peugeot", "2008");

        RestAssured
                .given()
                .contentType("application/json")
                .body(car)
                .when()
                .post("/api/car")
                .then()
                .statusCode(201)
                .body("maker", equalTo(car.getMaker()))
                .body("model", equalTo(car.getModel()));
    }

    @Test
    @Order(2)
    void whenFindCar_thenStatus200() {
        RestAssured
                .given()
                .contentType("application/json")
                .param("id", 1L)
                .when()
                .get("/api/car")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void whenFindCarMissing_thenStatus404() {
        RestAssured
                .given()
                .contentType("application/json")
                .param("id", 99)
                .when()
                .get("/api/car")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(2)
    void given3Cars_whenFindAll_thenReturnAll200() {
        RestAssured
                .given()
                .when()
                .get("/api/cars")
                .then()
                .statusCode(200)
                .body("carId", hasItems(1, 2, 3));
    }
}
