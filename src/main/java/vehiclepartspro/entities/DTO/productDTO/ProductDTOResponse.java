package vehiclepartspro.entities.DTO.productDTO;


import lombok.Getter;
import lombok.Setter;
import vehiclepartspro.entities.DTO.carDTO.CarDTOResponse;
import vehiclepartspro.entities.DTO.manufacturerDTO.ManufacturerDTOResponse;

import java.io.Serializable;

@Getter
@Setter

/**
 * Oggetto utilizzato per mappare il singolo prodotto del db
 */

public class ProductDTOResponse implements Serializable
{
    private String barCode;
    private String name;
    private double price;
    private String description;
    private CarDTOResponse car;
    private ManufacturerDTOResponse manufacturer;


}
