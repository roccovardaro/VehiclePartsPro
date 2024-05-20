package vehiclepartspro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiclepartspro.entities.Car;
import vehiclepartspro.entities.DTO.productDTO.ProductDTORequestInsert;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponse;
import vehiclepartspro.entities.Product;
import vehiclepartspro.services.ProductService;
import vehiclepartspro.support.exception.productException.CarNotFoundException;
import vehiclepartspro.support.exception.productException.ManufacturerNotFoundException;
import vehiclepartspro.support.exception.productException.NotNegativePriceException;
import vehiclepartspro.support.exception.purchaseException.NotNegativeQuantityException;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController
{
    @Autowired
    private ProductService productService;

    //da rivedere
    @GetMapping("/byCar")
    public ResponseEntity getAllProductsOfCar(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                              @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                                              @RequestBody Car car)
    {
        List<Product> products= productService.getAllProductsOfCar(car, pageNumber, pageSize, sortBy);
        return new ResponseEntity(products, HttpStatus.OK);
    }

    @GetMapping("byName")
    public ResponseEntity getAllProductsByName(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                                               @RequestParam(value = "name",required = true) String name)
    {
        List<ProductDTOResponse> productsDTO = productService.getAllProductsByName(name, pageNumber, pageSize, sortBy);
        return new ResponseEntity(productsDTO, HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public ResponseEntity addProductForBuy(@RequestBody ProductDTORequestInsert productDTO)
    {
        try
        {
            ProductDTOResponse productDTOResponse= productService.addProduct(productDTO);
            return new ResponseEntity(productDTOResponse, HttpStatus.OK);
        }
        catch (NotNegativeQuantityException e)
        {
            return new ResponseEntity("NOT_NEGATIVE_QUANTITY_EXCEPTION", HttpStatus.BAD_REQUEST);
        }
        catch (NotNegativePriceException e)
        {
            return new ResponseEntity("NOT_NEGATIVE_PRICE_EXCEPTION", HttpStatus.BAD_REQUEST);
        }
        catch (CarNotFoundException e)
        {
            return new ResponseEntity("CAR_NOT_FOUND_EXCEPTION", HttpStatus.BAD_REQUEST);
        }
        catch (ManufacturerNotFoundException e)
        {
            return new ResponseEntity("MANUFACTURER_NOT_FOUND_EXCEPTION", HttpStatus.BAD_REQUEST);
        }
    }



}
