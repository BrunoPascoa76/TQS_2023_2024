package ua.bp.lab_3_2.service;

import java.util.List;
import java.util.Optional;

import ua.bp.lab_3_2.data.Car;

public interface CarManagerService {

    public Car save(Car car);

    public List<Car> getAllCars();

    public Optional<Car> getCarDetails(Long carId);
}