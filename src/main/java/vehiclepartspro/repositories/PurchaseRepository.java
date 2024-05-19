package vehiclepartspro.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Purchase findOneById(int id);
    Page<Purchase> findByBuyer(User user, Pageable pageable);
    Page<Purchase> findByBuyerAndPurchaseTimeBetween(User buyer, Date fromDate, Date toDate, Pageable pageable);


}
