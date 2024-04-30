package vehiclepartspro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vehiclepartspro.entities.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>
{
    List<Car> findCarByBrand(String brand);
    List<Car> findCarByModelAndBrand(String model,String brand);
    boolean existsCarByBrandAndModelAndYear(String brand, String model, int year);

}
