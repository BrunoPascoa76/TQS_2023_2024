package ua.bp.lab_3_2;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import ua.bp.lab_3_2.controller.CarController;
import ua.bp.lab_3_2.data.Car;
import ua.bp.lab_3_2.service.CarManagerService;

import static org.hamcrest.Matchers.*;

@WebMvcTest(CarController.class)
public class CarRestAssuredControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CarManagerService service;

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car car = new Car("BMW", "Corolla");
        when(service.save(Mockito.any())).thenReturn(car);

        RestAssuredMockMvc
                .given()
                .mockMvc(mvc)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(car))
                .when()
                .post("/api/car")
                .then()
                .statusCode(201)
                .body("maker", equalTo("BMW"))
                .body("model", equalTo("Corolla"));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void whenGetAllCars_thenReturnAllCars() throws Exception {
        Car car1 = new Car("BMW", "Corolla");
        Car car2 = new Car("Nissan", "Ariya");
        Car car3 = new Car("Peugeot", "2008");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        when(service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc
                .given()
                .mockMvc(mvc)
                .when()
                .get("/api/cars")
                .then()
                .statusCode(200)
                .body("[0].maker", equalTo(car1.getMaker()))
                .body("[1].maker", equalTo(car2.getMaker()))
                .body("[2].maker", equalTo(car3.getMaker()))

                .body("[0].model", equalTo(car1.getModel()))
                .body("[1].model", equalTo(car2.getModel()))
                .body("[2].model", equalTo(car3.getModel()));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void whenGetCarExists_thenReturnCar() throws Exception {
        Car car = new Car("BMW", "Corolla");

        when(service.getCarDetails(1L)).thenReturn(Optional.of(car));

        RestAssuredMockMvc
                .given()
                .mockMvc(mvc)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .when()
                .get("/api/car")
                .then()
                .statusCode(200)
                .body("maker", equalTo(car.getMaker()))
                .body("model", equalTo(car.getModel()));

        verify(service, times(1)).getCarDetails(Mockito.any());
    }

    @Test
    void whenGetCarNotExists_thenReturnNotFound() throws Exception {
        when(service.getCarDetails(1L)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                .mockMvc(mvc)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .when()
                .get("/api/car")
                .then()
                .statusCode(404);

        verify(service, times(1)).getCarDetails(Mockito.any());
    }

}
