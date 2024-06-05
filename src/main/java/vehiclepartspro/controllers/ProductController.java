package vehiclepartspro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiclepartspro.entities.DTO.productDTO.ProductDTORequestInsert;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponse;
import vehiclepartspro.services.ProductService;
import vehiclepartspro.support.authentication.Utils;
import vehiclepartspro.support.exception.accountingException.QuantityIllegalException;
import vehiclepartspro.support.exception.productException.*;
import vehiclepartspro.support.exception.purchaseException.NotNegativeQuantityException;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController
{
    @Autowired
    private ProductService productService;

    //TODO da rivedere
    /*
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
    }*/

    @PostMapping("/addProduct")
    public ResponseEntity addProduct(@RequestBody ProductDTORequestInsert productDTO)
    {
        try
        {
            String email= Utils.getEmail();
            ProductDTOResponse productDTOResponse= productService.addProduct(productDTO,email);
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
        catch (NameProductNotValidException e)
        {
            return new ResponseEntity("NAME_PRODUCT_NOT_VALID_EXCEPTION", HttpStatus.BAD_REQUEST);
            
        }
        catch (IdProductIllegalException e)
        {
           return new ResponseEntity("ID_PRODUCT_ILLEGAL_EXCEPTION",HttpStatus.FORBIDDEN);
        }
        catch (Exception e)
        {
            return new ResponseEntity("GENERAL_ERROR",HttpStatus.BAD_REQUEST);

        }
    }
    @DeleteMapping("/deleteProduct")
    public ResponseEntity deleteProduct(@RequestParam(value = "id",required = true) int id,
                                        @RequestParam(value = "quantity", required = true) int quantity)
    {
        try
        {
            String emailUser= Utils.getEmail();
            String ret=productService.deleteProduct(id,quantity,emailUser);
            return new ResponseEntity(ret,HttpStatus.OK);
        } catch (QuantityIllegalException e)
        {
            return new ResponseEntity("QUANTITY_ILLEGAL_EXCEPTION",HttpStatus.BAD_REQUEST);
        } catch (IdProductIllegalException e)
        {
            return new ResponseEntity("ID_PRODUCT_ILLEGAL_EXCEPTION",HttpStatus.FORBIDDEN);
        } catch (ProductNotFoundException e)
        {
            return new ResponseEntity("PRODUCT_NOT_FOUND_EXCEPTION",HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity("GENERAL_ERROR",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity getProducts(@RequestParam(value = "name", required = true)String name,
                                      @RequestParam(value="pageNumber", defaultValue = "0") int pageNumber,
                                      @RequestParam(value= "pageSize", defaultValue = "5")int pageSize,
                                      @RequestParam(value="sortBy",defaultValue = "name") String sortBy)
    {
        try
        {

            List<ProductDTOResponse> ret = productService.getAllProductsByName(name, pageNumber, pageSize, sortBy);
            return new ResponseEntity(ret, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity("GENERAL_ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    //TODO fare il metodo getAllPRoductsByCar
    @GetMapping("/getAllProductsByCar")
    public ResponseEntity getProductsByCar
    (
            @RequestParam(value="pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value= "pageSize", defaultValue = "5")int pageSize,
            @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value="brand", required=true) String brand)
    {
        try
        {

            List<ProductDTOResponse> ret = productService.getAllProductsByCar(brand, pageNumber, pageSize, sortBy);
            return new ResponseEntity(ret, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity("GENERAL_ERROR", HttpStatus.BAD_REQUEST);
        }

    }




}
