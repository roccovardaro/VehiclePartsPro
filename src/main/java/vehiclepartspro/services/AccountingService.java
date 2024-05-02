package vehiclepartspro.services;

import vehiclepartspro.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vehiclepartspro.repositories.UserRepository;
import vehiclepartspro.support.exception.FiscalCodeUserExistsException;


@Service
public class AccountingService
{
    @Autowired
    UserRepository userRepository;

    public User UserSave(User user) throws FiscalCodeUserExistsException
    {

        String fiscalCode = user.getFiscalCode().trim().toUpperCase();
        if (userRepository.existsByFiscalCode(fiscalCode)) {
            throw new FiscalCodeUserExistsException();
        }
        user.setFiscalCode(fiscalCode);
        return userRepository.save(user);

    }
}
