package vehiclepartspro.entities.DTO.mapper;

import vehiclepartspro.entities.DTO.userDTO.UserDTO;
import vehiclepartspro.entities.User;

public class UserMapper
{
    public static UserDTO convertEntityToDTO(User user)
    {
        UserDTO userDTO= new UserDTO();
        userDTO.setFiscalCode(user.getFiscalCode());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setTelephoneNumber(userDTO.getTelephoneNumber());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(userDTO.getAddress());
        return userDTO;
    }

    public static User convertDTOtoEntity(UserDTO userDTO)
    {
        //todo verifica campi
        User user= new User();
        user.setFiscalCode(userDTO.getFiscalCode().trim().toUpperCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setTelephoneNumber(userDTO.getTelephoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        return user;
    }
}
