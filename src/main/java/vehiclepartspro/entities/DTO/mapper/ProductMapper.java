package vehiclepartspro.entities.DTO.mapper;

import vehiclepartspro.entities.DTO.carDTO.CarDTOResponse;
import vehiclepartspro.entities.DTO.manufacturerDTO.ManufacturerDTOResponse;
import vehiclepartspro.entities.DTO.productDTO.ProductDTORequestBuy;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponse;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponseBuy;
import vehiclepartspro.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper
{
    public static ProductDTOResponse convertToDTO(Product product)
    {
        ProductDTOResponse productDTOResponse = new ProductDTOResponse();
        productDTOResponse.setBarCode(product.getBarCode());
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
        product.setBarCode(productDTO.getBarCode());
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

}
