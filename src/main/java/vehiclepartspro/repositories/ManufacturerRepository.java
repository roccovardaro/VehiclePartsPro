package vehiclepartspro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vehiclepartspro.entities.Manufacturer;
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Integer>
{
    Manufacturer findManufacturerById(int id);
    boolean existsById(int id);
}
