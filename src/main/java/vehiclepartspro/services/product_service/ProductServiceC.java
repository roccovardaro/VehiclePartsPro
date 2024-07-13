package vehiclepartspro.services.product_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vehiclepartspro.entities.*;
import vehiclepartspro.entities.DTO.mapper.ProductMapper;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponse;
import vehiclepartspro.repositories.CarRepository;
import vehiclepartspro.repositories.ManufacturerRepository;
import vehiclepartspro.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceC
{
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductDTOResponse> getAllProductsByName(String name, int pageNumber, int pageSize, String sortBy)
    {
        List<ProductDTOResponse>retProductDTO= new ArrayList<>();
        Pageable page= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        String searchTerm= "%"+name+"%";
        Page<Product> pageResult= productRepository.findProductsByName(searchTerm,page);

       if(pageResult.hasContent())
       {
           List<Product> products = pageResult.getContent();
           for(Product product: products)
           {
               ProductDTOResponse productDTOResponse = ProductMapper.convertToDTO(product);
               retProductDTO.add(productDTOResponse);
           }
           return retProductDTO;
       }
       else
       {
           return new ArrayList<>();
       }

    }

    @Transactional(readOnly = true)
    public List<ProductDTOResponse> getAllProductsByCarBrand(String brand, int pageNumber, int pageSize, String sortBy)
    {
        List<ProductDTOResponse>retProductDTO= new ArrayList<>();
        Pageable page= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        String searchTerm= brand;
        Page<Product> pageResult= productRepository.findProductsByBrandCar(searchTerm,page);

        if(pageResult.hasContent())
        {
            List<Product> products = pageResult.getContent();
            for(Product product: products)
            {
                ProductDTOResponse productDTOResponse = ProductMapper.convertToDTO(product);
                retProductDTO.add(productDTOResponse);
            }
            return retProductDTO;
        }
        else
        {
            return new ArrayList<>();
        }

    }
}
