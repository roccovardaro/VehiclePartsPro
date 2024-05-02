package vehiclepartspro.services;

import org.springframework.stereotype.Service;
import vehiclepartspro.entities.User;

@Service
public interface UserService
{
    boolean UserSave(User user);
    boolean UserExists(String fiscal_code);


}
