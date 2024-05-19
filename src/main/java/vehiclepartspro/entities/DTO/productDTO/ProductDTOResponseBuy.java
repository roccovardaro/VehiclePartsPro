package vehiclepartspro.entities.DTO.productDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Oggetto DTO in cui viene mappato il prodotto acquistato (precisamente al momento dell'acquisto)
 * da ritornare tramite l'oggetto DTO {@link vehiclepartspro.entities.DTO.PurchaseDTO.PurchaseDTOResponseBuy}
 */
@Getter
@Setter
public class ProductDTOResponseBuy
{

    private int id;
    private String barCode;
    private String name;
    private double price;
    private int quantity;

}
