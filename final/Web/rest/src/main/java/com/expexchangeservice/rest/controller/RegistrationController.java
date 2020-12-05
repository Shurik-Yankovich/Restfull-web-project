package com.expexchangeservice.rest.controller;

import com.expexchangeservice.model.dto.RequestError;
import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.entities.Token;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.service.interfaces.ITokenService;
import com.expexchangeservice.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    private IUserService userService;
    private ITokenService tokenService;

    @Autowired
    public RegistrationController(IUserService userService, ITokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserDto userForm) {
        if (!userService.createUser(userForm)) {
            return new ResponseEntity<>(new RequestError(400,
                    "login isn't unique",
                    "current login is already exist"),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userForm) {
        if (userForm.getUsername() == null || userForm.getPassword() == null) {
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
        Token token = tokenService.generateToken(userForm);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(tokenService.AUTH_HEADER_NAME, token.getValue());
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

}
