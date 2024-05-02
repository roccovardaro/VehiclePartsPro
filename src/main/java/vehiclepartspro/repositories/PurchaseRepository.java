package vehiclepartspro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vehiclepartspro.entities.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer>
{

}
