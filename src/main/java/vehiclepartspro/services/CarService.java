package vehiclepartspro.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vehiclepartspro.entities.Car;
import vehiclepartspro.repositories.CarRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService
{
    @Autowired
    private CarRepository carRepository;


    public List<Car> allCarByBrand(String brand)
    {
        //ripuliamo la stringa scrivendola in minuscolo con spazi bianchi
        String brand_ok= brand.trim().toUpperCase();

        List<Car> cars= carRepository.findCarByBrand(brand_ok);
        return cars;
        }


    public List<Car> AllCarByBrandAndModel(String brand, String model) {
        List<Car> cars= carRepository.findCarByModelAndBrand(model,brand);
        if (cars.isEmpty())
        {
            return new ArrayList<>();
        }
        return cars;
    }


    public boolean ExistsByBrandAndModelAndYear(String brand, String model, int year)
    {
        return carRepository.existsCarByBrandAndModelAndYear(brand, model, year);
    }

    public List<Car> AllCars() {
        return carRepository.findAll();
    }
}
