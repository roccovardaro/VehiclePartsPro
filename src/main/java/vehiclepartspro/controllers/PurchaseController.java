package vehiclepartspro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vehiclepartspro.controllers.request.PurchaseRequest;
import vehiclepartspro.entities.Product;
import vehiclepartspro.entities.User;
import vehiclepartspro.services.PurchaseService;

@RestController
@RequestMapping("/purchases")
public class PurchaseController
{
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/buyProduct")
    public boolean buyProduct(@RequestBody PurchaseRequest p)
    {

        boolean fatto=purchaseService.addPurchase(p.getProduct(),p.getUser());
        return fatto;
    }


}
