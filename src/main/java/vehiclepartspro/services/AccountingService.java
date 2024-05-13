package vehiclepartspro.services;

import org.springframework.transaction.annotation.Transactional;
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
    public User UserSave(User user) throws FiscalCodeUserExistsException, MailUserExistsException {

        String fiscalCode = user.getFiscalCode().trim().toUpperCase();

        if (userRepository.existsByFiscalCode(fiscalCode)) {
            throw new FiscalCodeUserExistsException();
        }
        if( userRepository.existsByEmail(user.getEmail()))
        {
            throw new MailUserExistsException();
        }

        user.setFiscalCode(fiscalCode);
        return userRepository.save(user);
    }
}
