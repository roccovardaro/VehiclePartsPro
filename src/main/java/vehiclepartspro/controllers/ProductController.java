package vehiclepartspro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiclepartspro.entities.Car;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponse;
import vehiclepartspro.entities.Product;
import vehiclepartspro.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController
{
    @Autowired
    private ProductService productService;

    
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


}
