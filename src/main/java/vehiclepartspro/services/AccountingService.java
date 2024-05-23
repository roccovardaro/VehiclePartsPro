package vehiclepartspro.services;

import org.springframework.transaction.annotation.Transactional;
import vehiclepartspro.entities.DTO.userDTO.UserDTO;
import vehiclepartspro.entities.DTO.mapper.UserMapper;
import vehiclepartspro.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vehiclepartspro.entities.Manufacturer;
import vehiclepartspro.entities.User;
import vehiclepartspro.entities.enumeration.Role;
import vehiclepartspro.repositories.CustomerRepository;
import vehiclepartspro.repositories.ManufacturerRepository;
import vehiclepartspro.repositories.UserRepository;
import vehiclepartspro.support.exception.accountingException.FiscalCodeUserExistsException;
import vehiclepartspro.support.exception.accountingException.MailUserExistsException;


@Service
public class AccountingService
{
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;


    public UserDTO UserSave(UserDTO userDTO) throws MailUserExistsException
    {

        checkData(userDTO);

        User user = UserMapper.convertDTOtoEntity(userDTO);

        userRepository.save(user);

        //dopo averlo salvato negli utenti generali lo salviamo nella tabella che ha il ruolo corrispondente

        if(user.getRole().equals(Role.CUSTOMER))
        {
            Customer customer = new Customer();
            customer.setUser(user);
            customerRepository.save(customer);
        }
        //sicuramente sarà un manufacturer perchè la verifica del ruolo la facciamo nel metodo
        //utils.getRole()
        else

        {
            Manufacturer manufacturer= new Manufacturer();
            manufacturer.setUser(user);
            manufacturerRepository.save(manufacturer);
        }

        return userDTO;
    }

    private void checkData(UserDTO userDTO) throws MailUserExistsException
    {
        if(userRepository.existsByEmail(userDTO.getEmail()))
        {
            throw new MailUserExistsException();
        }
        //TODO altre verifiche da aggiungere

    }
}
