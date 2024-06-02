package vehiclepartspro.entities.DTO.PurchaseDTO;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter

public final class PurchaseDTO implements Serializable
{
    private int id;
    private Date purchaseDate;
    private List<ProductInPurchaseDTO> products= new ArrayList<>();





}
