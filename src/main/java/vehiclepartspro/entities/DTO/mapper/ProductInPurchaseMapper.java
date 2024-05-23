package vehiclepartspro.entities.DTO.mapper;

import vehiclepartspro.entities.DTO.PurchaseDTO.ProductInPurchaseDTO;
import vehiclepartspro.entities.ProductInPurchase;

public class ProductInPurchaseMapper
{

    public static ProductInPurchaseDTO convertToDTO(ProductInPurchase productInPurchase)
    {
        ProductInPurchaseDTO productInPurchaseDTO = new ProductInPurchaseDTO();
        productInPurchaseDTO.setProductId(productInPurchase.getProduct().getId());
        productInPurchaseDTO.setNameProduct(productInPurchase.getProduct().getName());
        productInPurchaseDTO.setQuantity(productInPurchase.getQuantity());
        productInPurchaseDTO.setManufacturer(productInPurchase.getProduct().getManufacturer().getUser().getFirstName());
        return productInPurchaseDTO;
    }
}
