package vehiclepartspro.services.product_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vehiclepartspro.entities.Car;
import vehiclepartspro.entities.DTO.mapper.ProductMapper;
import vehiclepartspro.entities.DTO.productDTO.ProductDTORequestInsert;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponse;
import vehiclepartspro.entities.Manufacturer;
import vehiclepartspro.entities.Product;
import vehiclepartspro.repositories.CarRepository;
import vehiclepartspro.repositories.ManufacturerRepository;
import vehiclepartspro.repositories.ProductRepository;
import vehiclepartspro.support.HandleFile;
import vehiclepartspro.support.exception.accountingException.QuantityIllegalException;
import vehiclepartspro.support.exception.productException.*;
import vehiclepartspro.support.exception.purchaseException.NotNegativeQuantityException;

import java.io.IOException;

@Service
public class ProductServiceM
{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;


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
    public ProductDTOResponse addProduct (ProductDTORequestInsert productDTO, String emailManufacturer) throws NotNegativeQuantityException, NotNegativePriceException, CarNotFoundException, ManufacturerNotFoundException, IdProductIllegalException, NameProductNotValidException, IOException {

        boolean existProduct= productRepository.existsById(productDTO.getId());
        checkDataAddProduct(productDTO,existProduct);

        //prendiamo il produttore dal db
        Manufacturer manufacturer= manufacturerRepository.findByUserEmail(emailManufacturer);
        //Se esiste il prodotto incrementiamo la quantità

        Product p_db;
        if(existProduct)
        {
            //prodotto del db
            p_db = productRepository.findById(productDTO.getId());

            //VERICHIAMO CHE IL PRODOTTO SIA ASSOCIATO AL MANUFACTURER THIS
            if (! p_db.getManufacturer().equals(manufacturer))
            {
                throw new IdProductIllegalException();
            }

            p_db.setQuantity(p_db.getQuantity()+productDTO.getQuantity());
        }
        //altrimenti se non esiste lo aggiungiamo
        else
        {
            //Prendo la Car e il Manufacturer dal db
            Car car= carRepository.findCarById(productDTO.getCar_id());
            p_db = ProductMapper.convertToEntity(productDTO,car,manufacturer);
            productRepository.save(p_db);

        }
        //salva immagine
        //HandleFile.uploadFile(file,p_db.getId());
        return ProductMapper.convertToDTO(p_db);
    }

    @Transactional(readOnly = true)
    protected void checkDataAddProduct(ProductDTORequestInsert productDTO, boolean existProduct) throws NotNegativePriceException, CarNotFoundException, NotNegativeQuantityException, NameProductNotValidException {

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
            if(productDTO.getName()==null)
            {
                throw new NameProductNotValidException();
            }
        }
        //verifica da fare sia se il prodotto esiste sia se non esiste
        if(productDTO.getQuantity()<0)
        {
            throw new NotNegativeQuantityException();
        }
    }

    /**
     * Eliminiamo la {@quantity} del prodotto con {@id}, se la quantità è uguale a
     * quella presente nel db questo viene eliminato.
     * @param id prodotto da eliminare
     * @param quantity del prodotto da eliminare
     * @param emailUser produttore associato al prodotto
     * @return String -> "PRODUCT HAS BEEN DELETED"
     * @throws ProductNotFoundException
     * @throws IdProductIllegalException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public String deleteProduct(int id, int quantity, String emailUser) throws ProductNotFoundException, IdProductIllegalException, QuantityIllegalException
    {
        if(quantity<0)
        {
            throw new QuantityIllegalException();
        }

        if(!productRepository.existsById(id))
        {
            throw new ProductNotFoundException();
        }
        Product p_db= productRepository.findById(id);
        //verifichiamo che il prodotto da eliminare sia associato al manufacturer this
        Manufacturer manufacturer= manufacturerRepository.findByUserEmail(emailUser);
        if (!p_db.getManufacturer().equals(manufacturer))
        {
            throw new IdProductIllegalException();
        }

        //FINE VERIFICHE

        //ELIMINIAMO IL PRODOTTO IN BASE ALLA QUANTITA'

        int quantity_db= p_db.getQuantity();

        if (quantity== quantity_db)
        {
            productRepository.delete(p_db);
            HandleFile.deleteImageProduct(p_db.getId());
        }
        else if (quantity< quantity_db)
        {
            p_db.setQuantity(quantity_db-quantity);
        }
        else
        {
            throw new QuantityIllegalException();
        }
        return "PRODUCT HAS BEEN DELETED";

    }

    //TODO da rivedere completamente getAllProductsOfCar()
    /*@Transactional(readOnly = true)
    public List<Product> getAllProductsOfCar(Car car, int pageNumber, int pageSize, String sortBy)
    {

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
    }*/
}
