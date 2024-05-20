package vehiclepartspro.entities.DTO.carDTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class CarDTORequest implements Serializable
{
    private String brand;
    private String model;
    private int year;
    private String description;

    private CarDTORequest() {}
}
