package vehiclepartspro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vehiclepartspro.entities.Manufacturer;
import vehiclepartspro.entities.User;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Integer>
{
    Manufacturer findByUser(User u);
    Manufacturer findById(Long id);
    Manufacturer findManufacturerById(Long id);
}
