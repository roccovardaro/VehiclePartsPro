package vehiclepartspro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiclepartspro.controllers.request.PurchaseListRequest;
import vehiclepartspro.entities.Product;
import vehiclepartspro.entities.ProductInPurchase;
import vehiclepartspro.entities.Purchase;
import vehiclepartspro.entities.User;
import vehiclepartspro.services.PurchaseService;
import vehiclepartspro.support.exception.purchaseException.ProductNotAvaiableException;
import vehiclepartspro.support.exception.purchaseException.QuantityProductNotAvaiableException;
import vehiclepartspro.support.exception.purchaseException.NotNegativeQuantityException;

import java.util.Calendar;
import java.util.Date;
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

    @GetMapping("/showPurchasedProducts")
    public ResponseEntity showPurchasedProducts
            (
            @RequestParam(value = "FromDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(value = "toDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(value= "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue= "5") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "purchaseTime") String sortBy,
            @RequestBody User user)
    {
        List<Purchase> ret;
        if(fromDate==null || toDate==null) {
            ret = purchaseService.getAllPurchasedProducts(user, pageNumber, pageSize, sortBy);
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        if(fromDate.after(toDate))
        {
            return new ResponseEntity<>("INVALID DATE FORMAT",HttpStatus.BAD_REQUEST);
        }
        ret= purchaseService.getAllPurchasedProducts(user, fromDate, toDate, pageNumber, pageSize, sortBy);
        return new ResponseEntity<>(ret, HttpStatus.OK);



    }

}
