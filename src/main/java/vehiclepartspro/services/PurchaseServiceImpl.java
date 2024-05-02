package vehiclepartspro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vehiclepartspro.entities.Product;
import vehiclepartspro.entities.ProductInPurchase;
import vehiclepartspro.entities.Purchase;
import vehiclepartspro.entities.User;
import vehiclepartspro.repositories.ProductInPurchaseRepository;
import vehiclepartspro.repositories.ProductRepository;
import vehiclepartspro.repositories.PurchaseRepository;
import vehiclepartspro.repositories.UserRepository;

import java.util.Date;

@Service
public class PurchaseServiceImpl implements PurchaseService
{
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean addPurchase(Product p, User u)
    {
        //prendiamo il prodotto dal db
        Product p1= productRepository.findByBarCode(p.getBarCode());
        //prendiamo l'utente dal db
        User u1= userRepository.findByFiscalCode(u.getFiscal_code());
        //verifico che il prodotto sia disponibile con il codice del prodotto nel db e la quantità del prodotto
        //passato
        if(avaiableProduct(p1.getId(), p.getQuantity()))
        {
            Purchase purchase = new Purchase();
            purchase.setBuyer(u1);
            purchase.setPurchaseTime(new Date());
            purchaseRepository.save(purchase);

            //sottraiamo la quantita acquistata
            //questa modifica verrà automaticamente propagata al database quando la transazione verrà committata.
            // Non è necessario chiamare save() di nuovo sul repository per salvare le modifiche
            p1.setQuantity(p1.getQuantity()- p.getQuantity());

            ProductInPurchase productInPurchase = new ProductInPurchase();
            productInPurchase.setProduct(p1);
            productInPurchase.setPurchase(purchase);
            productInPurchase.setQuantity(p.getQuantity());

            productInPurchaseRepository.save(productInPurchase);
            return true;
        }
        return false;
    }

    @Override
    public boolean avaiableProduct(int productID, int quantity)
    {
        return productRepository.findQuantityById(productID) >= quantity;

    }
}
