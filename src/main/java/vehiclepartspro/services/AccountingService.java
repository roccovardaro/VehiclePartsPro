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
import vehiclepartspro.support.exception.accountingException.LastNameNotValidException;
import vehiclepartspro.support.exception.accountingException.MailUserExistsException;
import vehiclepartspro.support.exception.accountingException.FirstNameNotValidException;
import vehiclepartspro.support.exception.accountingException.TelephoneNumberNotValidException;


@Service
public class AccountingService
{
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public UserDTO getUserDetail(String email)
    {
        User user = userRepository.findByEmail(email);
        UserDTO userDTO = UserMapper.convertEntityToDTO(user);
        return userDTO;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public UserDTO UserSave(UserDTO userDTO) throws MailUserExistsException, TelephoneNumberNotValidException, FirstNameNotValidException, LastNameNotValidException
    {

        checkDataUserSave(userDTO);

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

    /**
     * Metodo per verificare i dati dell'utente( ad esclusione di mail e role presi da server keycloak)
     * Applica il trim sui campi stringhe per rimuovere gli spazi vuoti
     * @param userDTO
     * @throws MailUserExistsException
     */

    @Transactional(readOnly = true)
    protected void checkDataUserSave(UserDTO userDTO) throws FirstNameNotValidException, LastNameNotValidException, TelephoneNumberNotValidException, MailUserExistsException
    {
        if(userRepository.existsByEmail(userDTO.getEmail()))
        {
            throw new MailUserExistsException();
        }
        if(userDTO.getTelephoneNumber()==null || userRepository.existsByTelephoneNumber(userDTO.getTelephoneNumber()))
        {
            throw new TelephoneNumberNotValidException();
        }

        //TODO altre verifiche da aggiungere
        if(userDTO.getFirstName()==null)
        {
            throw new FirstNameNotValidException();
        }
        if(userDTO.getLastName()==null)
        {
            throw new LastNameNotValidException();
        }
    }
}
