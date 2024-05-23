package vehiclepartspro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiclepartspro.entities.DTO.userDTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import vehiclepartspro.entities.User;
import vehiclepartspro.services.AccountingService;
import vehiclepartspro.support.authentication.Utils;
import vehiclepartspro.support.exception.accountingException.MailUserExistsException;

import javax.management.relation.Role;
import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/users")
public class AccountingController
{

    @Autowired
    private AccountingService accountingService;
    /*
    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody UserDTO userDTO)
    {
        try
        {
            Customer added= accountingService.UserSave(userDTO);
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

    }*/


    @PostMapping("/saveUser")
    public ResponseEntity UserSignUp(@RequestBody UserDTO userDTO) throws MailUserExistsException, RoleNotFoundException {
        //la mail e il ruolo li prendiamo dal JWT
        try {


            userDTO.setEmail(Utils.getEmail());
            userDTO.setRole(Utils.getRole().toString());
            UserDTO user = accountingService.UserSave(userDTO);
            return new ResponseEntity(user, HttpStatus.CREATED);
        }
        catch (MailUserExistsException e)
        {
            return new ResponseEntity("MAIL_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);
        }
        catch (RoleNotFoundException e)
        {
            return new ResponseEntity("ROLE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }


    }


    @GetMapping("/prova")
    public String ProvaSecurityOauth() throws RoleNotFoundException
    {
        System.out.println(Utils.getEmail());
        System.out.println(Utils.getRole());

        return "accesso consentito";
    }
}