package vehiclepartspro.services;

import org.springframework.stereotype.Service;
import vehiclepartspro.entities.Car;

import java.util.List;

@Service
public interface CarService
{
    List<Car> allCarByBrand(String brand);
    List<Car>AllCarByBrandAndModel(String brand, String model);
    boolean ExistsByBrandAndModelAndYear(String brand, String model,int year);
    List<Car>AllCars();


}
