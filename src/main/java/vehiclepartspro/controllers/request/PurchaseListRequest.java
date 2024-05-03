package vehiclepartspro.controllers.request;

import lombok.Getter;
import vehiclepartspro.entities.Product;
import vehiclepartspro.entities.User;

import java.util.Collections;
import java.util.List;

public final class PurchaseListRequest
{
    private List<Product> products;
    @Getter
    private User user;

    public PurchaseListRequest(List<Product> products, User user) {
        this.products = products;
        this.user = user;
    }
    public List<Product> getProducts()
    {
        return products;
    }
}
