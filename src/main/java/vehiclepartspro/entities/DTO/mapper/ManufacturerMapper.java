package vehiclepartspro.entities.DTO.mapper;

import vehiclepartspro.entities.DTO.manufacturerDTO.ManufacturerDTOResponse;
import vehiclepartspro.entities.Manufacturer;

public class ManufacturerMapper
{

    public static ManufacturerDTOResponse convertToDTO(Manufacturer manufacturer)
    {
        ManufacturerDTOResponse manufacturerDTOResponse = new ManufacturerDTOResponse();
        manufacturerDTOResponse.setName(manufacturer.getName());
        return manufacturerDTOResponse;
    }
}
