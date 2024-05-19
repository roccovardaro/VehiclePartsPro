package vehiclepartspro.entities.DTO.mapper;

import vehiclepartspro.entities.DTO.PurchaseDTO.PurchaseDTO;
import vehiclepartspro.entities.DTO.PurchaseDTO.ProductInPurchaseDTO;
import vehiclepartspro.entities.ProductInPurchase;
import vehiclepartspro.entities.Purchase;

import java.util.List;

public class PurchaseMapper
{
    public static PurchaseDTO convertToDTO(Purchase purchase)
    {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setId(purchase.getId());
        purchaseDTO.setPurchaseDate(purchase.getPurchaseTime());
        purchaseDTO.setFiscalCodeBuyer(purchase.getBuyer().getFiscalCode());

        List<ProductInPurchase> products = purchase.getProductsInPurchase();
        for (ProductInPurchase productInPurchase : products)
        {
            ProductInPurchaseDTO purchasedProductDTO = ProductInPurchaseMapper.convertToDTO(productInPurchase);
            purchaseDTO.getProducts().add(purchasedProductDTO);
        }
        return purchaseDTO;
    }

}
