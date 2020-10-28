package restcontroller;

import dto.UserDto;
import dto.UserRegistrationDto;
import entity.RequestError;
import entity.security.Token;
import entity.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.security.UserService;
import util.converter.DtoConverter;
import util.security.TokenHandler;


@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenHandler tokenHandler;

    @PostMapping("/registration")
    public ResponseEntity<?> addUser(@RequestBody UserRegistrationDto userForm) {

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            return new ResponseEntity<>(new RequestError(400,
                    "passwords do not match",
                    "password and password confirmation do not match"),
                    HttpStatus.BAD_REQUEST);
        }
        if (!userService.saveUser(DtoConverter.convertRegistrationDtoToUser(userForm))) {
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
//        User user = userService.loadUserByUsername(userForm.getUsername());
        User user = userService.findByUsernameAndPassword(userForm.getUsername(), userForm.getPassword());
        if (user == null) {
            return new ResponseEntity<>(new RequestError(404,
                    "current user not found",
                    "current user deleted or not created"),
                    HttpStatus.NOT_FOUND);
        }
        Token token = new Token(tokenHandler.generateToken(user.getId().toString()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(tokenHandler.AUTH_HEADER_NAME, token.getValue());
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
//        if(user != null) {
//            Integer userId = user.getUser().getId();
//            return ResponseEntity.ok(new Token(tokenHandler.getToken(userId.toString())));
//        } else {
//            return new ResponseEntity<>(new RequestError(404,
//                    "current user not found",
//                    "current user deleted or not created"),
//                    HttpStatus.NOT_FOUND);
//        }
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader(tokenHandler.AUTH_HEADER_NAME) String token){
//
//    }
}
