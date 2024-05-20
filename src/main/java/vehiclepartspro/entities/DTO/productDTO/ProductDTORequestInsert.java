package vehiclepartspro.entities.DTO.productDTO;

import lombok.Getter;

import java.io.Serializable;

/**
 * Oggetto DTO utilizzato per passare nelle richieste HTTP il prodotto da inserire nel db
 */

@Getter

public class ProductDTORequestInsert implements Serializable
{
    private String name;
    private String bar_code;
    private String description;
    private float price;
    private int quantity;
    private int car_id;
    private int manufacturer_id;

}
