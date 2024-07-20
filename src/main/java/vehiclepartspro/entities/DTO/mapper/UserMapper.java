package vehiclepartspro.entities.DTO.mapper;

import vehiclepartspro.entities.DTO.userDTO.UserDTO;
import vehiclepartspro.entities.Customer;
import vehiclepartspro.entities.User;
import vehiclepartspro.entities.enumeration.Role;

public class UserMapper
{
    public static UserDTO convertEntityToDTO(User user)
    {
        UserDTO userDTO= new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setTelephoneNumber(user.getTelephoneNumber());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setRole(user.getRole().toString());
        return userDTO;
    }

    public static User convertDTOtoEntity(UserDTO userDTO)
    {
        User user= new User();
        user.setFirstName(userDTO.getFirstName()!=null ? userDTO.getFirstName().trim():null);
        user.setLastName(userDTO.getLastName()!=null ? userDTO.getLastName().trim():null);
        user.setTelephoneNumber(userDTO.getTelephoneNumber()!=null ? userDTO.getTelephoneNumber().trim():null);
        user.setEmail(userDTO.getEmail()!=null ? userDTO.getEmail().trim():null);
        user.setAddress(userDTO.getAddress()!=null ? userDTO.getAddress().trim():null);
        user.setRole(Role.valueOf(userDTO.getRole()));
        return user;
    }
}
