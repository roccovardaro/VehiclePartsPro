package vehiclepartspro.controllers.request;

import vehiclepartspro.entities.Product;
import vehiclepartspro.entities.Customer;

public final class PurchaseRequest
{
    private Customer user;
    private Product product;

    public Customer getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public PurchaseRequest(Customer user, Product product) {
        this.user = user;
        this.product = product;
    }
}
