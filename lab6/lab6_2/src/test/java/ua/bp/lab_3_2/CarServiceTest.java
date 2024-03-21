package ua.bp.lab_3_2;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.bp.lab_3_2.data.Car;
import ua.bp.lab_3_2.data.CarRepository;
import ua.bp.lab_3_2.service.CarManagerServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    @Mock(lenient = true)
    private CarRepository repo;

    @InjectMocks
    private CarManagerServiceImpl service;

    @BeforeEach
    public void setup() {
        Car car1 = new Car("BMW", "Corolla");
        Car car2 = new Car("Nissan", "Ariya");
        Car car3 = new Car("Peugeot", "2008");
        car1.setCarId(1L);

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        Mockito.when(repo.findAll()).thenReturn(allCars);
        Mockito.when(repo.findByCarId(car1.getCarId())).thenReturn(car1);
        Mockito.when(repo.findByCarId(car2.getCarId())).thenReturn(car2);
        Mockito.when(repo.findByCarId(car3.getCarId())).thenReturn(car3);
        Mockito.when(repo.findByCarId(99L)).thenReturn(null);
    }

    @Test
    void given3Cars_whenGetAllCars_thenReturn3Cars() {
        Car car1 = new Car("BMW", "Corolla");
        Car car2 = new Car("Nissan", "Ariya");
        Car car3 = new Car("Peugeot", "2008");

        List<Car> allCars = service.getAllCars();

        assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(car1.getMaker(), car2.getMaker(),
                car3.getMaker());
    }

    @Test
    void whenGetCorrectId_ThenReturnCar(){
        Optional<Car> car=service.getCarDetails(1L);

        assertTrue(car.isPresent());
        assertEquals("BMW", car.get().getMaker());
    }

    @Test
    void whenGetWrongId_ThenReturnEMpty(){
        Optional<Car> car=service.getCarDetails(99L);
        assertFalse(car.isPresent());
    }
}
