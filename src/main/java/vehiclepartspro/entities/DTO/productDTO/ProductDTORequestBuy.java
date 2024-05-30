package vehiclepartspro.entities.DTO.productDTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * Classe per passare i prodotti da acquistare nella richiesta HTTP
 */
@Getter

public class ProductDTORequestBuy implements Serializable
{

    private int id;
    private int quantity;

    public ProductDTORequestBuy(int id, int quantity)
    {
        this.id = id;
        this.quantity = quantity;
    }
}
