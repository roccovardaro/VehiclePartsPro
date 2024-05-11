package vehiclepartspro.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vehiclepartspro.entities.Car;
import vehiclepartspro.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{

    Product findByBarCode(String barCode);
    boolean existsProductByBarCode(String barCode);
    Page<Product> findAllByCar(Car car, Pageable pageable);


    @Query("Select p from Product p where p.name like ?1")
    Page<Product> findProductsByName(String name, Pageable pageable);


}


