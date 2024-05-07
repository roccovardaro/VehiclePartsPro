package vehiclepartspro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vehiclepartspro.entities.Product;
import vehiclepartspro.repositories.ProductRepository;
import vehiclepartspro.support.exception.NotNegativePriceException;
import vehiclepartspro.support.exception.purchaseException.NotNegativeQuantityException;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = false)

    public Product addProduct (Product product) throws NotNegativeQuantityException, NotNegativePriceException
    {
        if(product.getQuantity()<0)
        {
            throw new NotNegativeQuantityException();
        }
        if(product.getPrice()<0)
        {
            throw new NotNegativePriceException();
        }
        if(productRepository.existsProductByBarCode(product.getBarCode().trim().toUpperCase()))
        {
            //prodotto del db
            Product p= productRepository.findByBarCode(product.getBarCode().trim().toUpperCase());
            p.setQuantity(p.getQuantity()+product.getQuantity());
            return p;
        }

        Product p= productCreate(product);
        productRepository.save(p);
        return p;

    }

    private Product productCreate(Product product)
    {
        Product p = new Product();
        p.setName(product.getName());
        p.setBarCode(product.getBarCode().trim().toUpperCase());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setQuantity(product.getQuantity());
        return p;

    }

    //TODO getAllProductsOfCar(Car car)


}
