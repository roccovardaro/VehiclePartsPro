package vehiclepartspro.entities.DTO.productDTO;

import lombok.Getter;

import java.io.Serializable;

/**
 * Oggetto DTO utilizzato per passare nelle richieste HTTP il prodotto da inserire nel db
 */

@Getter

public class ProductDTORequestInsert implements Serializable
{
    private int id;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private int car_id;

}
