package vehiclepartspro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vehiclepartspro.entities.DTO.userDTO.UserDTO;
import vehiclepartspro.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vehiclepartspro.services.AccountingService;
import vehiclepartspro.support.exception.accountingException.FiscalCodeUserExistsException;
import vehiclepartspro.support.exception.accountingException.MailUserExistsException;

@RestController
@RequestMapping("/users")
public class AccountingController
{

    @Autowired
    private AccountingService accountingService;

    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody UserDTO userDTO)
    {
        try
        {
            User added= accountingService.UserSave(userDTO);
            return new ResponseEntity<>(added, HttpStatus.OK);

        }
        catch (FiscalCodeUserExistsException f)
        {
            return new ResponseEntity<>("ERROR_FISCAL_CODE_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);
        }
        catch (MailUserExistsException m)
        {
            return new ResponseEntity<>("ERROR_MAIL_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);
        }

    }
}