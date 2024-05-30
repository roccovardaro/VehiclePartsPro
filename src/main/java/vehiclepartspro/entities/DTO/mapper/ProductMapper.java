package vehiclepartspro.entities.DTO.mapper;

import vehiclepartspro.entities.Car;
import vehiclepartspro.entities.DTO.carDTO.CarDTOResponse;
import vehiclepartspro.entities.DTO.manufacturerDTO.ManufacturerDTOResponse;
import vehiclepartspro.entities.DTO.productDTO.ProductDTORequestBuy;
import vehiclepartspro.entities.DTO.productDTO.ProductDTORequestInsert;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponse;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponseBuy;
import vehiclepartspro.entities.Manufacturer;
import vehiclepartspro.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper
{
    public static ProductDTOResponse convertToDTO(Product product)
    {
        ProductDTOResponse productDTOResponse = new ProductDTOResponse();
        productDTOResponse.setId(product.getId());
        productDTOResponse.setDescription(product.getDescription());
        productDTOResponse.setPrice(product.getPrice());
        productDTOResponse.setName(product.getName());
        //costruisco il CarDTOResponse
        CarDTOResponse carDTOResponse= CarMapper.convertToDTO(product.getCar());
        productDTOResponse.setCar(carDTOResponse);
        //costruisco il ManufacturerDTOResponse
        ManufacturerDTOResponse manufacturerDTOResponse= ManufacturerMapper.convertToDTO(product.getManufacturer());
        productDTOResponse.setManufacturer(manufacturerDTOResponse);
        return productDTOResponse;
    }

    public static Product convertToEntity(ProductDTORequestBuy productDTO)
    {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setQuantity(productDTO.getQuantity());
        return product;
    }

    public static List<Product> convertToEntity(List<ProductDTORequestBuy> productsDTO)
    {
        List<Product> products = new ArrayList<>();

        for(ProductDTORequestBuy productDTO : productsDTO)
        {
            Product product = convertToEntity(productDTO);
            products.add(product);
        }
        return products;
    }

    public static ProductDTOResponseBuy convertToDTO(Product product, int quantity_purchase)
    {
        ProductDTOResponseBuy productDTOResponse = new ProductDTOResponseBuy();
        productDTOResponse.setBarCode(product.getBarCode());
        productDTOResponse.setQuantity(quantity_purchase);
        productDTOResponse.setPrice(product.getPrice());
        productDTOResponse.setName(product.getName());
        productDTOResponse.setId(product.getId());
        return productDTOResponse;
    }

    /**
     * Convertiamo il DTO nel prodotto da inserire nel database
     * @param productDTO
     * @param car
     * @param manufacturer
     * @return
     */
    public static Product convertToEntity(ProductDTORequestInsert productDTO, Car car, Manufacturer manufacturer)
    {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setManufacturer(manufacturer);
        product.setQuantity(productDTO.getQuantity());
        product.setCar(car);
        return product;
    }

}
