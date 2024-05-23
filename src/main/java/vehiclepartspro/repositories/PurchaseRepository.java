package vehiclepartspro.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vehiclepartspro.entities.Purchase;
import vehiclepartspro.entities.Customer;

import java.util.Date;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer>
{

    Purchase findOneById(int id);
    Page<Purchase> findByBuyer(Customer user, Pageable pageable);
    Page<Purchase> findByBuyerAndPurchaseTimeBetween(Customer buyer, Date fromDate, Date toDate, Pageable pageable);


}
