package vehiclepartspro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiclepartspro.controllers.request.PurchaseListRequest;
import vehiclepartspro.entities.Product;
import vehiclepartspro.services.PurchaseService;
import vehiclepartspro.support.exception.purchaseException.ProductNotAvaiableException;
import vehiclepartspro.support.exception.purchaseException.QuantityProductNotAvaiableException;
import vehiclepartspro.support.exception.purchaseException.NotNegativeQuantityException;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController
{
    @Autowired
    private PurchaseService purchaseService;

    //TODO Controllare funzionamento metodo
    @PostMapping("/buyProducts")
    public ResponseEntity buyProducts(@RequestBody PurchaseListRequest p)
    {
        try
        {
            List<Product> ret= purchaseService.addPurchases(p.getProducts(),p.getUser());
            return new ResponseEntity<>(ret,HttpStatus.OK);
        }
        catch (QuantityProductNotAvaiableException | ProductNotAvaiableException | NotNegativeQuantityException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
