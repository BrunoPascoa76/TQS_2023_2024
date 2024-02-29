package ua.bp.lab_3_2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.bp.lab_3_2.data.Car;
import ua.bp.lab_3_2.data.CarRepository;

@Service
public class CarManagerServiceImpl implements CarManagerService {
    @Autowired
    private CarRepository repo;

    public Car save(Car car){
        repo.save(car);
        return car;
    }

    public List<Car> getAllCars(){
        return repo.findAll();
    }

    public Optional<Car> getCarDetails(Long id){
        Car car=repo.findByCarId(id);
        if(car!=null){
            return Optional.of(car);
        }else{
            return Optional.empty();
        }
    }
}
