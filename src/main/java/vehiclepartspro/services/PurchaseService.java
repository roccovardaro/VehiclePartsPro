package vehiclepartspro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.List;


import java.util.Date;

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

    @Transactional(rollbackFor = {Exception.class})
    public List<Product> addPurchases(List<Product> products, User u) throws QuantityProductNotAvaiableException, ProductNotAvaiableException, NotNegativeQuantityException {
        List<Product> ret= new ArrayList<>();

        for (Product p: products)
        {

            Product prodotto = addPurchase(p, u);
            ret.add(prodotto);
        }
        return ret;
    }


    @Transactional(readOnly = false)
    protected Product addPurchase(Product p, User u) throws NotNegativeQuantityException, ProductNotAvaiableException, QuantityProductNotAvaiableException {
        //prendiamo il prodotto dal db

        if(p.getQuantity()<0)
        {
            throw new NotNegativeQuantityException(p);
        }

        if(!productRepository.existsProductByBarCode(p.getBarCode().trim().toUpperCase()))
        {
            throw new ProductNotAvaiableException(p);
        }
        Product p1= productRepository.findByBarCode(p.getBarCode().trim().toUpperCase());

        //prendiamo l'utente dal db
        User u1= userRepository.findByFiscalCode(u.getFiscalCode().trim().toUpperCase());
        //verifico che il prodotto sia disponibile con il codice del prodotto nel db e la quantità del prodotto
        //passato

        if(!avaiableProduct(p1.getId(),p.getQuantity()))
        {
            throw new QuantityProductNotAvaiableException(p);
        }

        //DOPO LA VERIFICA DELLA QUANTITA´ PROCEDIAMO CON L'ACQUISTO

        Purchase purchase = new Purchase();
        purchase.setBuyer(u1);
        purchase.setPurchaseTime(new Date());
        purchaseRepository.save(purchase);

        //Sottraiamo la quantita acquistata
        //questa modifica verrà automaticamente propagata al database quando la transazione verrà committata.
        // Non è necessario chiamare save() di nuovo sul repository per salvare le modifiche
        p1.setQuantity(p1.getQuantity()- p.getQuantity());

        ProductInPurchase productInPurchase = new ProductInPurchase();
        productInPurchase.setProduct(p1);
        productInPurchase.setPurchase(purchase);
        productInPurchase.setQuantity(p.getQuantity());

        productInPurchaseRepository.save(productInPurchase);
        return p;
    }

    private boolean avaiableProduct(int productID, int quantity)
    {
        return productRepository.findQuantityById(productID) >= quantity;
    }

    //TODO metodo getAllUserPurchases()
    //TODO metodo getAllUserPurchases(Date from, Date to)


}
