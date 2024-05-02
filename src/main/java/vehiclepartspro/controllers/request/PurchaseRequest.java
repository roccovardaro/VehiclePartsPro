package vehiclepartspro.controllers.request;

import vehiclepartspro.entities.Product;
import vehiclepartspro.entities.User;

public final class PurchaseRequest
{
    private User user;
    private Product product;

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public PurchaseRequest(User user, Product product) {
        this.user = user;
        this.product = product;
    }
}
