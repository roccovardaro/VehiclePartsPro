package vehiclepartspro.entities.DTO.carDTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CarDTOResponse implements Serializable
{
    private String brand;
    private String model;
    private int year;
}
