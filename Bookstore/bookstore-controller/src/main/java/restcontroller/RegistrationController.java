package restcontroller;

import dto.UserDto;
import entity.RequestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.security.UserService;
import util.converter.DtoConverter;


@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> addUser(@RequestBody UserDto userForm) {

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            return new ResponseEntity<>(new RequestError(400,
                    "passwords do not match",
                    "password and password confirmation do not match"),
                    HttpStatus.BAD_REQUEST);
        }
        if (!userService.saveUser(DtoConverter.convertDtoToUser(userForm))) {
            return new ResponseEntity<>(new RequestError(400,
                    "login isn't unique",
                    "current login is already exist"),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
//        return "redirect:/";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userForm){
        if(userForm.getUsername() == null || userForm.getPassword() == null) {
            return new ResponseEntity<>(new RequestError(400,
                    "request json error",
                    "request json must include existing login and pass"),
                    HttpStatus.BAD_REQUEST);
        }
        UserCreds userCreds = userCredsService.getUserCredsByLoginAndPass(userForm.getLogin(),
                userForm.getPass());
        if(userCreds != null) {
            Integer userId = userCreds.getUser().getId();
            return ResponseEntity.ok(new Token(tokenHandler.getToken(userId.toString())));
        } else {
            return new ResponseEntity<>(new RequestError(404,
                    "current user not found",
                    "current user deleted or not created"),
                    HttpStatus.NOT_FOUND);
        }
    }
}
