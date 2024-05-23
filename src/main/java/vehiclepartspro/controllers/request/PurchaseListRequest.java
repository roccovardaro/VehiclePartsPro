package vehiclepartspro.controllers.request;

import lombok.Getter;
import vehiclepartspro.entities.Product;
import vehiclepartspro.entities.Customer;

import java.util.List;

public final class PurchaseListRequest
{
    private List<Product> products;
    @Getter
    private Customer user;

    public PurchaseListRequest(List<Product> products, Customer user) {
        this.products = products;
        this.user = user;
    }
    public List<Product> getProducts()
    {
        return products;
    }
}
