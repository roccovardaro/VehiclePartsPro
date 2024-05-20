package vehiclepartspro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vehiclepartspro.entities.Car;
import vehiclepartspro.entities.DTO.mapper.ProductMapper;
import vehiclepartspro.entities.DTO.productDTO.ProductDTORequestInsert;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponse;
import vehiclepartspro.entities.Manufacturer;
import vehiclepartspro.entities.Product;
import vehiclepartspro.repositories.CarRepository;
import vehiclepartspro.repositories.ManufacturerRepository;
import vehiclepartspro.repositories.ProductRepository;
import vehiclepartspro.support.exception.productException.CarNotFoundException;
import vehiclepartspro.support.exception.productException.ManufacturerNotFoundException;
import vehiclepartspro.support.exception.productException.NotNegativePriceException;
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
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    //TODO metodi per inserire prodotti nel db

    /**
     * Inserisce un prodotto all'interno del db
     * @param productDTO oggettoDTO contenente le informazioni del prodotto da inserire
     * @return {@link ProductDTOResponse} DTO che incapsula le informazioni di ritorno del prodotto inserito
     * @throws NotNegativeQuantityException
     * @throws NotNegativePriceException
     * @throws CarNotFoundException
     * @throws ManufacturerNotFoundException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ProductDTOResponse addProduct (ProductDTORequestInsert productDTO) throws NotNegativeQuantityException, NotNegativePriceException, CarNotFoundException, ManufacturerNotFoundException {
        //VERIFICHE CAMPI PRODUCTDTO
        boolean existProduct= productRepository.existsProductByBarCode(productDTO.getBar_code().trim().toUpperCase());

        if(! existProduct)
        {
            //verifiche da fare solo se il prodotto non è presente nel db
            if(productDTO.getPrice()<0)
            {
                throw new NotNegativePriceException();
            }
            if(! carRepository.existsCarById(productDTO.getCar_id()))
            {
                throw new CarNotFoundException();
            }
            if (! manufacturerRepository.existsById(productDTO.getManufacturer_id()))
            {
                throw new ManufacturerNotFoundException();
            }
        }
        //verifica da fare sia se il prodotto esiste sia se non esiste
        if(productDTO.getQuantity()<0)
        {
            throw new NotNegativeQuantityException();
        }
        //FINE VERIFICHE CAMPI PRODUCTDTO


        if(existProduct)
        {
            //prodotto del db
            Product p_db = productRepository.findByBarCode(productDTO.getBar_code().trim().toUpperCase());
            p_db.setQuantity(p_db.getQuantity()+productDTO.getQuantity());
            return ProductMapper.convertToDTO(p_db);
        }
        //altrimenti se non esiste lo aggiungiamo
        else
        {
            //Prendo la Car e il Manufacturer dal db
            Car car= carRepository.findCarById(productDTO.getCar_id());
            Manufacturer manufacturer=manufacturerRepository.findManufacturerById(productDTO.getManufacturer_id());
            Product p_db = ProductMapper.convertToEntity(productDTO,car,manufacturer);
            productRepository.save(p_db);
            return ProductMapper.convertToDTO(p_db);

        }

    }


    //TODO passare il CarDTO al posto di Car
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

}
