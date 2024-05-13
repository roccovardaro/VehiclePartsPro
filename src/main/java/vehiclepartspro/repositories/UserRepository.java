package vehiclepartspro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vehiclepartspro.entities.User;

@Repository
public interface UserRepository
extends JpaRepository<User, Long>
{
    User findByFiscalCode(String fiscalCode);
    boolean existsByFiscalCode(String fiscalCode);
    boolean existsByEmail(String email);

}
