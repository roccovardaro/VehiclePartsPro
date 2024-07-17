package vehiclepartspro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiclepartspro.entities.DTO.PurchaseDTO.PurchaseDTO;
import vehiclepartspro.entities.DTO.PurchaseDTO.PurchaseDTOResponseBuy;
import vehiclepartspro.entities.DTO.productDTO.ProductDTORequestBuy;
import vehiclepartspro.services.PurchaseService;
import vehiclepartspro.support.authentication.Utils;
import vehiclepartspro.support.exception.purchaseException.ProductNotAvaiableException;
import vehiclepartspro.support.exception.purchaseException.QuantityProductNotAvaiableException;
import vehiclepartspro.support.exception.purchaseException.NotNegativeQuantityException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController
{
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/buyProducts")
    public ResponseEntity buyProducts(@RequestBody List<ProductDTORequestBuy> products)
    {
        try
        {
            String emailCustomer= Utils.getEmail();
            PurchaseDTOResponseBuy purchaseDTOResponseBuy = purchaseService.addPurchases(products,emailCustomer);
            return new ResponseEntity<>(purchaseDTOResponseBuy,HttpStatus.OK);
        }
        catch (QuantityProductNotAvaiableException | ProductNotAvaiableException | NotNegativeQuantityException e)
        {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/showPurchasedProducts")
    public ResponseEntity showPurchasedProducts
            (
            @RequestParam(value = "fromDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(value = "toDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(value= "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue= "5") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "purchaseTime") String sortBy,
            @RequestParam (value = "direction",defaultValue = "D") String direction)
    {
        try
        {


            String email = Utils.getEmail();
            List<PurchaseDTO> retDTO;
            if (fromDate == null || toDate == null) {
                retDTO = purchaseService.getAllPurchasedProducts(email, pageNumber, pageSize, sortBy, direction);
                return new ResponseEntity<>(retDTO, HttpStatus.OK);
            }
            if (fromDate.after(toDate)) {
                return new ResponseEntity<>("INVALID DATE FORMAT", HttpStatus.BAD_REQUEST);
            }
            retDTO = purchaseService.getAllPurchasedProducts(email, fromDate, toDate, pageNumber, pageSize, sortBy, direction);
            return new ResponseEntity<>(retDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("GENERAL_ERROR", HttpStatus.BAD_REQUEST);

        }

    }

}
