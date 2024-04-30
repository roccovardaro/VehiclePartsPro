package vehiclepartspro.services;

import org.springframework.http.ResponseEntity;
import vehiclepartspro.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vehiclepartspro.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean UserSave(User user)
    {

        if(!UserExists(user.getFirstName(), user.getLastName()))
        {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean UserExists(String firstName, String lastName)
    {
        return userRepository.existsByFirstNameAndLastName(firstName, lastName);
    }
}
