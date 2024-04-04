package ua.bp.lab_3_2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.bp.lab_3_2.data.Car;
import ua.bp.lab_3_2.service.CarManagerService;

@RestController
@RequestMapping("/api")
public class CarController {
    private final CarManagerService manager;

    public CarController(CarManagerService manager) {
        this.manager = manager;
    }

    @PostMapping("/car")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car saved = manager.save(car);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/cars")
    public List<Car> getAllCars(){
        return manager.getAllCars();
    }

    @GetMapping("/car")
    public ResponseEntity<Car> getCarById(@RequestParam Long id){
        Optional<Car> car=manager.getCarDetails(id);
        if(car.isPresent()){
            return new ResponseEntity<>(car.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
