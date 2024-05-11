package vehiclepartspro.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vehiclepartspro.entities.Car;
import vehiclepartspro.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{
    @Query("select p.quantity from Product p where p.id=?1")
    int findQuantityById(int productID);

    Product findByBarCode(String barCode);
    boolean existsProductByBarCode(String barCode);
    Page<Product> findAllByCar(Car car, Pageable pageable);


}


