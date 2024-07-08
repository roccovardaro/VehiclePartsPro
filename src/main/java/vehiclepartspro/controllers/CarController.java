package vehiclepartspro.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vehiclepartspro.services.CarService;

@RestController
@RequestMapping("/cars")
public class CarController
{
    //TODO da rivedere
    @Autowired
    CarService carService;

    @GetMapping("/allBrand")
    public ResponseEntity allBrandOfCar()
    {
        return new ResponseEntity<>(carService.allModel(), HttpStatus.OK);
    }
}
