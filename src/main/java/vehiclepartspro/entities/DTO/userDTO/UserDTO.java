package vehiclepartspro.entities.DTO.userDTO;

import lombok.Getter;
import lombok.Setter;
import vehiclepartspro.entities.enumeration.Role;

import java.io.Serializable;

@Setter
@Getter
public class UserDTO implements Serializable
{
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String email;
    private String address;
    private String role;
    public UserDTO() {}
}
