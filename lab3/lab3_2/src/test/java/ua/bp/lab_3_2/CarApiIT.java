package ua.bp.lab_3_2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.jboss.jandex.ParameterizedType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.context.TestPropertySource;

import ua.bp.lab_3_2.data.Car;
import ua.bp.lab_3_2.data.CarRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:it.properties")
class CarApiIT {
    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repo;

    @AfterEach
    public void resetDb() {
        repo.deleteAll();
    }

    @Test
    void whenValidCar_thenCreateCar() {
        Car car = new Car("BMW", "Corolla");
        ResponseEntity<Car> response = restTemplate.postForEntity("/api/car", car, Car.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Car result = response.getBody();

        assertAll(
                () -> assertEquals(car.getMaker(), result.getMaker()),
                () -> assertEquals(car.getModel(), result.getModel()));
    }

    @Test
    void whenFindCar_thenStatus200() {
        Car car1 = new Car("BMW", "Corolla");

        car1 = repo.saveAndFlush(car1);

        ResponseEntity<Car> response = restTemplate.getForEntity("/api/car?id=" + car1.getCarId(), Car.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(car1, response.getBody());
    }

    @Test
    void whenFindCarMissing_thenStatus404() {
        ResponseEntity<Car> response = restTemplate.getForEntity("/api/car?id=99", Car.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void given3Cars_whenFindAll_thenReturnAll200() {
        Car car1 = new Car("BMW", "Corolla");
        Car car2 = new Car("Nissan", "Ariya");
        Car car3 = new Car("Peugeot", "2008");

        car1 = repo.save(car1);
        car2 = repo.save(car2);
        car3 = repo.save(car3);
        repo.flush();

        ResponseEntity<List<Car>> response = restTemplate.exchange("/api/cars", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Car>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).hasSize(3).containsOnly(car1, car2, car3);
    }
}
