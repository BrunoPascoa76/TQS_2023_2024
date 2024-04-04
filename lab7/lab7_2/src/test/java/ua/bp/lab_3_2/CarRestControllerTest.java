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

import ua.bp.lab_3_2.controller.CarController;
import ua.bp.lab_3_2.data.Car;
import ua.bp.lab_3_2.service.CarManagerService;

@WebMvcTest(CarController.class)
public class CarRestControllerTest {
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
        mvc.perform(
                post("/api/car").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker").value("BMW"))
                .andExpect(jsonPath("$.model").value("Corolla"));
        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void whenGetAllCars_thenReturnAllCars() throws Exception {
        Car car1 = new Car("BMW", "Corolla");
        Car car2 = new Car("Nissan", "Ariya");
        Car car3 = new Car("Peugeot", "2008");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        when(service.getAllCars()).thenReturn(allCars);

        mvc.perform(
                get("/api/cars").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].maker").value(car1.getMaker()))
                .andExpect(jsonPath("$[1].maker").value(car2.getMaker()))
                .andExpect(jsonPath("$[2].maker").value(car3.getMaker()))

                .andExpect(jsonPath("$[0].model").value(car1.getModel()))
                .andExpect(jsonPath("$[1].model").value(car2.getModel()))
                .andExpect(jsonPath("$[2].model").value(car3.getModel()));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void whenGetCarExists_thenReturnCar() throws Exception {
        Car car = new Car("BMW", "Corolla");

        when(service.getCarDetails(1L)).thenReturn(Optional.of(car));

        mvc.perform(
                get("/api/car").param("id", "1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.maker").value(car.getMaker()))
                .andExpect(jsonPath("$.model").value(car.getModel()));

        verify(service, times(1)).getCarDetails(Mockito.any());
    }

    @Test
    void whenGetCarNotExists_thenReturnNotFound() throws Exception {
        when(service.getCarDetails(1L)).thenReturn(Optional.empty());

        mvc.perform(
                get("/api/car").param("id", "1")).andExpect(status().isNotFound());

        verify(service, times(1)).getCarDetails(Mockito.any());
    }

}
