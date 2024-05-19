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

    private String barCode;
    private int quantity;

    public ProductDTORequestBuy(String barCode, int quantity)
    {
        this.barCode = barCode;
        this.quantity = quantity;
    }
}
