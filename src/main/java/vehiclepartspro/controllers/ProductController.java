package vehiclepartspro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vehiclepartspro.entities.DTO.productDTO.ProductDTORequestInsert;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponse;
import vehiclepartspro.services.product_service.ProductServiceC;
import vehiclepartspro.services.product_service.ProductServiceM;
import vehiclepartspro.support.authentication.Utils;
import vehiclepartspro.support.exception.accountingException.QuantityIllegalException;
import vehiclepartspro.support.exception.productException.*;
import vehiclepartspro.support.exception.purchaseException.NotNegativeQuantityException;

import java.io.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController
{
    @Autowired
    private ProductServiceC productServiceC;
    @Autowired
    private ProductServiceM productServiceM;


    @PostMapping(value = "/addProduct")
    public ResponseEntity addProduct(@RequestBody ProductDTORequestInsert productDTO)
    {
        try
        {
            String email= Utils.getEmail();
            ProductDTOResponse productDTOResponse= productServiceM.addProduct(productDTO,email);
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

    @PostMapping("/addImageProduct/{productId}")
    public ResponseEntity addImageProduct(@PathVariable("productId") String productId, @RequestPart("productImage") MultipartFile file)
    {
        try
        {
            productServiceM.addImageProd(file,Integer.parseInt(productId));
            return new ResponseEntity("Success", HttpStatus.OK);
        }
        catch (IOException e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/deleteProduct")
    public ResponseEntity deleteProduct(@RequestParam(value = "id",required = true) int id,
                                        @RequestParam(value = "quantity", required = true) int quantity)
    {
        try
        {
            String emailUser= Utils.getEmail();
            String ret=productServiceM.deleteProduct(id,quantity,emailUser);
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

            List<ProductDTOResponse> ret = productServiceC.getAllProductsByName(name, pageNumber, pageSize, sortBy);
            return new ResponseEntity(ret, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity("GENERAL_ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllProductsByBrand")
    public ResponseEntity getProductsByBrand
    (
            @RequestParam(value="pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value= "pageSize", defaultValue = "5")int pageSize,
            @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value="brand", required=true) String brand)
    {
        try
        {

            List<ProductDTOResponse> ret = productServiceC.getAllProductsByCarBrand(brand, pageNumber, pageSize, sortBy);
            return new ResponseEntity(ret, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity("GENERAL_ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> getImage(@RequestParam(value = "idImage", required = false) int idImage) {
        try {
            // Costruisce il percorso relativo al file nel classpath

            String imagePath = "/Users/roccopiovardaro/Desktop/Java_Projects/VehiclePartsPro/src/main/java/vehiclepartspro/other/imageProduct/"+idImage+".png";
            InputStream in = new FileInputStream(imagePath);

            if (in == null) {
                return new ResponseEntity<>("Image not found".getBytes(), HttpStatus.NOT_FOUND);
            }

            byte[] imageBytes;
            try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = in.read(data, 0, data.length)) != -1)
                {
                    buffer.write(data, 0, nRead);
                }
                imageBytes = buffer.toByteArray();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(("Error loading image: " + e.getMessage()).getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
