package vehiclepartspro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vehiclepartspro.entities.DTO.PurchaseDTO.PurchaseDTO;
import vehiclepartspro.entities.DTO.PurchaseDTO.PurchaseDTOResponseBuy;
import vehiclepartspro.entities.DTO.mapper.ProductMapper;
import vehiclepartspro.entities.DTO.mapper.PurchaseMapper;
import vehiclepartspro.entities.DTO.productDTO.ProductDTORequestBuy;
import vehiclepartspro.entities.DTO.productDTO.ProductDTOResponseBuy;
import vehiclepartspro.entities.Product;
import vehiclepartspro.entities.ProductInPurchase;
import vehiclepartspro.entities.Purchase;
import vehiclepartspro.entities.User;
import vehiclepartspro.repositories.ProductInPurchaseRepository;
import vehiclepartspro.repositories.ProductRepository;
import vehiclepartspro.repositories.PurchaseRepository;
import vehiclepartspro.repositories.UserRepository;
import vehiclepartspro.support.exception.purchaseException.ProductNotAvaiableException;
import vehiclepartspro.support.exception.purchaseException.QuantityProductNotAvaiableException;
import vehiclepartspro.support.exception.purchaseException.NotNegativeQuantityException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseService
{
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param productsDTO prodotto da Inserire
     * @param fiscalCode del cliente che ha effettuato l'ordine
     * @return PurchaseDTOResponseBuy -> DTO del Purchase (quando si fa l'acquisto) che contiene i DTO dei prodotti acquistati
     * @throws QuantityProductNotAvaiableException
     * @throws ProductNotAvaiableException
     * @throws NotNegativeQuantityException
     */
    @Transactional(rollbackFor = {Exception.class})
    public PurchaseDTOResponseBuy addPurchases(List<ProductDTORequestBuy> productsDTO, String fiscalCode) throws QuantityProductNotAvaiableException, ProductNotAvaiableException, NotNegativeQuantityException {

        //prendiamo l'utente dal db
        //TODO fare verifiche sull'utente

        User u_db = userRepository.findByFiscalCode(fiscalCode.trim().toUpperCase());

        //creo l'acquisto che va fatto e passo il purchase a ogni prodotto da acquistare (in questo acquisto)
        Purchase purchase = new Purchase();
        purchase.setBuyer(u_db);
        purchase.setPurchaseTime(new Date());
        purchaseRepository.save(purchase);

        List<Product> products= ProductMapper.convertToEntity(productsDTO);
        double total_price=0;
        //creo il PurchaseDTOResponseBuy
        PurchaseDTOResponseBuy purchaseDTOResponseBuy = new PurchaseDTOResponseBuy();
        purchaseDTOResponseBuy.setPurchaseId(purchase.getId());

        for (Product p: products)
        {

            Product p_db = addProductInPurchase(p,purchase);
            total_price+=p_db.getPrice();
            //creo il DTO
            ProductDTOResponseBuy productDTOResponseBuy = ProductMapper.convertToDTO(p_db,p.getQuantity());
            purchaseDTOResponseBuy.getProducts().add(productDTOResponseBuy);
            //metto la quantità acquistata dell'oggetto
        }
        purchaseDTOResponseBuy.setTotal_price(total_price);
        return purchaseDTOResponseBuy;
    }

    /**
     * Inserisce il prodotto nel db -> metodo utilizzato nel metodo {@link #addPurchases(List, String)}
     * @param p Prodotto da inserire nell'acquisto
     * @param purchase acquisto
     * @return il prodotto acquistato (quello del db)
     * @throws NotNegativeQuantityException
     * @throws ProductNotAvaiableException
     * @throws QuantityProductNotAvaiableException
     */
    @Transactional(readOnly = false)
    protected Product addProductInPurchase(Product p, Purchase purchase) throws NotNegativeQuantityException, ProductNotAvaiableException, QuantityProductNotAvaiableException {
        //prendiamo il prodotto dal db

        if(p.getQuantity()<0)
        {
            throw new NotNegativeQuantityException(p);
        }

        if(!productRepository.existsProductByBarCode(p.getBarCode().trim().toUpperCase()))
        {
            throw new ProductNotAvaiableException(p);
        }

        //prendo il prodotto dal db
        Product p_db = productRepository.findByBarCode(p.getBarCode().trim().toUpperCase());


        //verifico che il prodotto sia disponibile con il codice del prodotto nel db e la quantità del prodotto
        //passato

        if(!avaiableProduct(p_db.getQuantity(),p.getQuantity()))
        {
            throw new QuantityProductNotAvaiableException(p_db);
        }

        //DOPO LA VERIFICA DELLA QUANTITA´ PROCEDIAMO CON L'AGGIUNGERE IL PRODOTTO NELL'ACQUISTO(PRODUCTINPURCHASE)

        //Sottraiamo la quantita acquistata
        //questa modifica verrà automaticamente propagata al database quando la transazione verrà committata.
        // Non è necessario chiamare save() di nuovo sul repository per salvare le modifiche
        p_db.setQuantity(p_db.getQuantity()- p.getQuantity());

        ProductInPurchase productInPurchase = new ProductInPurchase();
        productInPurchase.setProduct(p_db);
        productInPurchase.setPurchase(purchase);
        productInPurchase.setQuantity(p.getQuantity());

        productInPurchaseRepository.save(productInPurchase);

        return p_db;
    }

    private boolean avaiableProduct(int productQuantity , int quantity)
    {
        return productQuantity>= quantity;
    }


    @Transactional(readOnly = true)
    public List<PurchaseDTO> getAllPurchasedProducts(String fiscalCode,int pageNumber, int pageSize, String sortBy)
    {
        return getAllPurchasedProducts(fiscalCode,null,null,pageNumber,pageSize,sortBy);
    }

    @Transactional(readOnly = true)
    public List<PurchaseDTO> getAllPurchasedProducts(String fiscalCode, Date fromDate, Date toDate, int pageNumber, int pageSize,String sortBy)
    {
        List<PurchaseDTO> retDTO = new ArrayList<>();
        //mi prendo la lista degli acquisti dell'utente
        User u_db= userRepository.findByFiscalCode(fiscalCode.trim().toUpperCase());
        //creiamo il pageable
        Pageable pageable= PageRequest.of(pageNumber,pageSize,Sort.by(sortBy));
        Page<Purchase> purchases;
        //se sono null prendiamo tutti gli ordini dell'utente
        if (fromDate == null && toDate == null)
            purchases = purchaseRepository.findByBuyer(u_db,pageable);
        else
            purchases=purchaseRepository.findByBuyerAndPurchaseTimeBetween(u_db, fromDate, toDate,pageable);

        if (purchases.hasContent())
        {
            List<Purchase> purchasesList = purchases.getContent();
            for(Purchase purchase : purchasesList)
            {
                PurchaseDTO purchaseDTO= PurchaseMapper.convertToDTO(purchase);
                retDTO.add(purchaseDTO);
            }
            return retDTO;
        }
        return new ArrayList<>();
    }


}
