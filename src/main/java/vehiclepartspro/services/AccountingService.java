package vehiclepartspro.services;

import org.springframework.transaction.annotation.Transactional;
import vehiclepartspro.entities.DTO.userDTO.UserDTO;
import vehiclepartspro.entities.DTO.mapper.UserMapper;
import vehiclepartspro.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vehiclepartspro.repositories.UserRepository;
import vehiclepartspro.support.exception.accountingException.FiscalCodeUserExistsException;
import vehiclepartspro.support.exception.accountingException.MailUserExistsException;


@Service
public class AccountingService
{
    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = false)
    public User UserSave(UserDTO userDTO) throws FiscalCodeUserExistsException, MailUserExistsException {

        //todo fare opportuni controlli
        User user= UserMapper.convertDTOtoEntity(userDTO);
        if (userRepository.existsByFiscalCode(user.getFiscalCode()))
        {
            throw new FiscalCodeUserExistsException();
        }
        if( userRepository.existsByEmail(user.getEmail()))
        {
            throw new MailUserExistsException();
        }

        return userRepository.save(user);
    }
}
