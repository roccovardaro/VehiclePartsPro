package vehiclepartspro.services;

import org.springframework.stereotype.Service;
import vehiclepartspro.entities.Product;
import vehiclepartspro.entities.Purchase;
import vehiclepartspro.entities.User;

@Service
public interface PurchaseService
{
    boolean addPurchase(Product p, User u);
    boolean avaiableProduct(int productId, int quantity);






}
