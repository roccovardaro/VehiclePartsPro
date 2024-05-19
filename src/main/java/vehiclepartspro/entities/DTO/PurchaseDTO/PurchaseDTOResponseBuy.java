package vehiclepartspro.entities.DTO.PurchaseDTO;

import lombok.Getter;
import lombok.Setter;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponseBuy;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilizzata quando viene restituito l'acquisto dei prodotti al momento dell'acquisto
 */
@Getter
@Setter
public class PurchaseDTOResponseBuy
{
    private int purchaseId;
    private double total_price;
    private List<ProductDTOResponseBuy> products = new ArrayList<>();

}
