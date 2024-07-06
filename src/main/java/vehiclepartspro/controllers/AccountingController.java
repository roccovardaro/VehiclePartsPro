package vehiclepartspro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiclepartspro.entities.DTO.userDTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import vehiclepartspro.services.AccountingService;
import vehiclepartspro.support.authentication.Utils;
import vehiclepartspro.support.exception.accountingException.*;


@RestController
@RequestMapping("/users")
public class AccountingController
{

    @Autowired
    private AccountingService accountingService;

    @PostMapping("/saveUser")
    public ResponseEntity UserSignUp(@RequestBody UserDTO userDTO)
    {
        //la mail e il ruolo li prendiamo dal JWT
        try
        {
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
        } catch (TelephoneNumberNotValidException e) {
            return new ResponseEntity("TELEPHONE_NUMBER_NOT_VALID", HttpStatus.BAD_REQUEST);
        } catch (FirstNameNotValidException e) {
            return new ResponseEntity("FIRST_NAME_NOT_VALID", HttpStatus.BAD_REQUEST);
        } catch (LastNameNotValidException e) {
            return new ResponseEntity("LAST_NAME_NOT_VALID", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity("GENERAL_ERROR", HttpStatus.BAD_REQUEST);
        }
    }
}