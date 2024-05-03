package vehiclepartspro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiclepartspro.controllers.request.PurchaseListRequest;
import vehiclepartspro.controllers.request.PurchaseRequest;
import vehiclepartspro.entities.Product;
import vehiclepartspro.services.PurchaseService;
import vehiclepartspro.support.exception.ProductNotAvaiableException;
import vehiclepartspro.support.exception.QuantityProductNotAvaiableException;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController
{
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/buyProduct")
    public ResponseEntity buySingleProduct(@RequestBody PurchaseRequest p)
    {
        try
        {
            Product p_ret= purchaseService.addPurchase(p.getProduct(),p.getUser());
            return new ResponseEntity<>(p_ret,HttpStatus.OK);
        }
        catch (QuantityProductNotAvaiableException | ProductNotAvaiableException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    //TODO Controllare funzionamento metodo
    @PostMapping("/buyProducts")
    public ResponseEntity buyProducts(@RequestBody PurchaseListRequest p)
    {
        try
        {
            List<Product> ret= purchaseService.addPurchases(p.getProducts(),p.getUser());
            return new ResponseEntity<>(ret,HttpStatus.OK);
        }
        catch (QuantityProductNotAvaiableException | ProductNotAvaiableException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
