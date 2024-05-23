package vehiclepartspro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vehiclepartspro.entities.Customer;
import vehiclepartspro.entities.User;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    Customer findByUser(User u);
}
