package com.expexchangeservice.controller.rest;

import com.expexchangeservice.model.dto.RequestError;
import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.entities.Token;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.service.converter.DtoConverter;
import com.expexchangeservice.service.interfaces.IUserService;
import com.expexchangeservice.utils.security.ITokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private IUserService userService;
    private ITokenHandler tokenHandler;

    @Autowired
    public RegistrationController(IUserService userService, ITokenHandler tokenHandler) {
        this.userService = userService;
        this.tokenHandler = tokenHandler;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserDto userForm) {

//        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
//            return new ResponseEntity<>(new RequestError(400,
//                    "passwords do not match",
//                    "password and password confirmation do not match"),
//                    HttpStatus.BAD_REQUEST);
//        }
        if (!userService.saveUser(DtoConverter.convertDtoToUser(userForm))) {
            return new ResponseEntity<>(new RequestError(400,
                    "login isn't unique",
                    "current login is already exist"),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userForm){
        if(userForm.getUsername() == null || userForm.getPassword() == null) {
            return new ResponseEntity<>(new RequestError(400,
                    "request json error",
                    "request json must include existing login and pass"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = userService.loadUserByUsernameAndPassword(userForm.getUsername(), userForm.getPassword());
        if (user == null) {
            return new ResponseEntity<>(new RequestError(404,
                    "current user not found",
                    "current user deleted or not created"),
                    HttpStatus.NOT_FOUND);
        }
        Token token = new Token(tokenHandler.generateToken(String.valueOf(user.getId())));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(tokenHandler.AUTH_HEADER_NAME, token.getValue());
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader(tokenHandler.AUTH_HEADER_NAME) String token){
//
//    }
}
