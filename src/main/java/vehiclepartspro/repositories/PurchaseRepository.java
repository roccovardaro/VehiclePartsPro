package vehiclepartspro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vehiclepartspro.entities.Product;
import vehiclepartspro.entities.ProductInPurchase;
import vehiclepartspro.entities.Purchase;
import vehiclepartspro.entities.User;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer>
{
    List<Purchase> findByBuyer(User user);
    List<Purchase> findByBuyerAndPurchaseTimeBetween(User buyer, Date fromDate, Date toDate);


}
