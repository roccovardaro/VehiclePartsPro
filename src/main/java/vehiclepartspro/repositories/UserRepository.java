package vehiclepartspro.repositories;

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
    User findByCode(String code);
    boolean existsByEmail(String email);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);


}