package vehiclepartspro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vehiclepartspro.entities.ProductInPurchase;

@Repository
public interface ProductInPurchaseRepository extends JpaRepository<ProductInPurchase, Integer>
{
}
