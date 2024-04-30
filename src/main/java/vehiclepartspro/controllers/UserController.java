package vehiclepartspro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vehiclepartspro.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vehiclepartspro.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody User user)
    {
        boolean insert= userService.UserSave(user);
        if(insert)
        {
            return new ResponseEntity<String>("USER_INSERTED", HttpStatus.OK);
        }
        return new ResponseEntity<String>("USER_NOT_INSERTED", HttpStatus.OK);
    }
}