package vehiclepartspro.entities.DTO.PurchaseDTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public final class ProductInPurchaseDTO implements Serializable
{
    private int  productId;
    private String nameProduct;
    private int quantity;
    private String Manufacturer;

}
