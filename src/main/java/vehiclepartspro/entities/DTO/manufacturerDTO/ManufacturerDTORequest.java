package vehiclepartspro.entities.DTO.manufacturerDTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class ManufacturerDTORequest implements Serializable
{
    private String name;
    private String email;
    private String telephoneNumber;
    private String address;

    public ManufacturerDTORequest(String name, String email, String telephoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
    }
}
