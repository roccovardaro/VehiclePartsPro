package vehiclepartspro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vehiclepartspro.entities.Car;
import vehiclepartspro.entities.Product;
import vehiclepartspro.repositories.CarRepository;
import vehiclepartspro.repositories.ProductRepository;
import vehiclepartspro.support.exception.NotNegativePriceException;
import vehiclepartspro.support.exception.purchaseException.NotNegativeQuantityException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CarRepository carRepository;

    /*public Product addProduct (Product product) throws NotNegativeQuantityException, NotNegativePriceException
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

    }*/

    public List<Product> getAllProductsOfCar(Car car, int pageNumber, int pageSize, String sortBy)
    {
        //TODO verifiche da fare

        //prendo la car dal db
        Car car_db= carRepository.findCarByModelAndYearAndBrand(car.getModel().trim().toUpperCase(), car.getYear(), car.getBrand().trim().toUpperCase());

        Pageable page= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Product> pagedResult= productRepository.findAllByCar(car_db,page);

        if(pagedResult.hasContent()) //facciamo il controllo perchè può restituire null
        {
            return pagedResult.getContent();
        }
        else
        {
            return new ArrayList<>();
        }
    }

}
