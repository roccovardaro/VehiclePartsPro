package vehiclepartspro.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vehiclepartspro.entities.Car;
import vehiclepartspro.services.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController
{
    @Autowired
    CarService carService;
    @GetMapping("/brand")
    public ResponseEntity<List<Car>> CarsByBrand(@RequestParam() String brand)
    {
        List<Car> cars= carService.allCarByBrand(brand);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Car>> allCars()
    {
        List<Car> allCars = carService.AllCars();
        return new ResponseEntity<>(allCars, HttpStatus.OK);
    }
}
