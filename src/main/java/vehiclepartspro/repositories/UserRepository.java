package vehiclepartspro.repositories;

import org.springframework.data.jpa.repository.Query;
import vehiclepartspro.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    List<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    @Query("select u from User u where u.fiscal_code=?1")
    User findByFiscalCode(String fiscal_code);



}