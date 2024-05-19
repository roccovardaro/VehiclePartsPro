package vehiclepartspro.entities.DTO.mapper;

import vehiclepartspro.entities.Car;
import vehiclepartspro.entities.DTO.carDTO.CarDTOResponse;

public class CarMapper
{
    public static CarDTOResponse convertToDTO(Car car)
    {
        CarDTOResponse carDTOResponse = new CarDTOResponse();
        carDTOResponse.setBrand(car.getBrand());
        carDTOResponse.setModel(car.getModel());
        carDTOResponse.setYear(car.getYear());
        return carDTOResponse;
    }

}
