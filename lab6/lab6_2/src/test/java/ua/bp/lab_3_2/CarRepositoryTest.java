package ua.bp.lab_3_2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ua.bp.lab_3_2.data.Car;
import ua.bp.lab_3_2.data.CarRepository;

@DataJpaTest
class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository repo;

    @Test
    void whenFindCarById_thenReturnCar() {
        Car car1 = new Car("Toyota", "Corolla");
        car1 = entityManager.persistAndFlush(car1);

        Car found = repo.findByCarId(car1.getCarId());
        assertEquals(car1, found);
    }

    @Test
    void whenFindInvalidCar_thenReturnNull() {
        Car notFound = repo.findByCarId(99L);
        assertNull(notFound);
    }

    @Test
    void given3Cars_whenFindAll_thenReturnAll(){
        Car car1 = new Car("BMW", "Corolla");
        Car car2 = new Car("Nissan", "Ariya");
        Car car3 = new Car("Peugeot", "2008");

        car1=entityManager.persist(car1);
        car2=entityManager.persist(car2);
        car3=entityManager.persist(car3);
        entityManager.flush();

        List<Car> allCars = repo.findAll();
        assertThat(allCars).containsOnly(car1,car2,car3);
    }
}
