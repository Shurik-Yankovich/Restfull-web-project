package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.entities.Token;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.model.entities.UserAuthentication;
import com.expexchangeservice.model.enums.Role;
import com.expexchangeservice.repository.interfaces.ITokenRepository;
import com.expexchangeservice.service.config.TokenServiceTestConfiguration;
import com.expexchangeservice.service.interfaces.ITokenService;
import com.expexchangeservice.service.interfaces.IUserService;
import com.expexchangeservice.utils.security.ITokenHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TokenServiceTestConfiguration.class)
public class TokenServiceTest {

    @Autowired
    private ITokenService tokenService;
    @Autowired
    private ITokenRepository tokenRepository;
    @Autowired
    private ITokenHandler tokenHandler;
    @Autowired
    private IUserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    private static User EXPECTED_USER;
    private static UserDto EXPECTED_USER_DTO;
    private static UserAuthentication EXPECTED_USER_AUTH;
    private static Token EXPECTED_TOKEN;
    private static final long ID = 1L;
    private static final String EXPECTED_USERNAME = "user";
    private static final String TOKEN_VALUE = "ytg5ka_qk`io_qwl8kdf";
    private static final String AUTH_HEADER_NAME = "Auth-Token";

    @BeforeAll
    public static void init() {
        String password = "123";
        String cryptPassword = "ytg5ka_qk";
        EXPECTED_USER = new User();
        EXPECTED_USER.setId(ID);
        EXPECTED_USER.setUsername(EXPECTED_USERNAME);
        EXPECTED_USER.setPassword(cryptPassword);
        EXPECTED_USER.setRole(Role.ROLE_ADMIN);
        EXPECTED_USER_AUTH = new UserAuthentication(EXPECTED_USER);
        EXPECTED_USER_DTO = new UserDto();
        EXPECTED_USER_DTO.setUsername(EXPECTED_USERNAME);
        EXPECTED_USER_DTO.setPassword(password);
        EXPECTED_TOKEN = new Token(TOKEN_VALUE, EXPECTED_USER);
    }

    @Test
    public void getAuthenticationWithoutErrors() {
        doReturn(TOKEN_VALUE).when(httpServletRequest).getHeader(AUTH_HEADER_NAME);
        doReturn(true).when(tokenHandler).checkToken(TOKEN_VALUE);
        doReturn(EXPECTED_TOKEN).when(tokenRepository).findByValue(TOKEN_VALUE);
        doReturn(ID).when(tokenHandler).getUserIdFromToken(TOKEN_VALUE);
        doReturn(EXPECTED_USER).when(userService).loadUserById(ID);

        Authentication actualAuthentication = tokenService.getAuthentication(httpServletRequest);
        assertEquals(EXPECTED_USER_AUTH, actualAuthentication);
    }

    @Test
    public void getAuthenticationWhenTokenValueIsNotValid() {
        doReturn(TOKEN_VALUE).when(httpServletRequest).getHeader(AUTH_HEADER_NAME);
        doReturn(false).when(tokenHandler).checkToken(TOKEN_VALUE);

        assertNull(tokenService.getAuthentication(httpServletRequest));
    }

    @Test
    public void generateTokenWithoutErrors() {
        doReturn(EXPECTED_USER).when(userService).loadUserByUsername(EXPECTED_USERNAME);
        doReturn(TOKEN_VALUE).when(tokenHandler).generateToken(ID);
        doNothing().when(tokenRepository).create(any(Token.class));

        Token actualToken = tokenService.generateToken(EXPECTED_USER_DTO);
        assertEquals(EXPECTED_TOKEN, actualToken);
    }

    @Test
    public void generateTokenWhenUserNotInDatabase() {
        doReturn(null).when(userService).loadUserByUsername(EXPECTED_USERNAME);

        assertNull(tokenService.generateToken(EXPECTED_USER_DTO));
    }

    @Test
    public void deleteTokenWithoutErrors() {
        doReturn(TOKEN_VALUE).when(httpServletRequest).getHeader(AUTH_HEADER_NAME);
        doReturn(EXPECTED_TOKEN).when(tokenRepository).findByValue(TOKEN_VALUE);
        doNothing().when(tokenRepository).deleteByUser(EXPECTED_USER);

        assertTrue(tokenService.deleteToken(httpServletRequest));
    }

    @Test
    public void deleteTokenWhenTokenNotInDatabase() {
        doReturn(TOKEN_VALUE).when(httpServletRequest).getHeader(AUTH_HEADER_NAME);
        doReturn(null).when(tokenRepository).findByValue(TOKEN_VALUE);

        assertFalse(tokenService.deleteToken(httpServletRequest));
    }

    @Test
    public void checkUserByUsernameWithoutErrors() {
        doReturn(TOKEN_VALUE).when(httpServletRequest).getHeader(AUTH_HEADER_NAME);
        doReturn(ID).when(tokenHandler).getUserIdFromToken(TOKEN_VALUE);
        doReturn(EXPECTED_USER).when(userService).loadUserById(ID);

        assertTrue(tokenService.checkUser(httpServletRequest, EXPECTED_USERNAME));
    }

    @Test
    public void checkUserByUsernameWhenUsernameDoesNotMatch() {
        String actualUsername = "admin";

        doReturn(TOKEN_VALUE).when(httpServletRequest).getHeader(AUTH_HEADER_NAME);
        doReturn(ID).when(tokenHandler).getUserIdFromToken(TOKEN_VALUE);
        doReturn(EXPECTED_USER).when(userService).loadUserById(ID);

        assertFalse(tokenService.checkUser(httpServletRequest, actualUsername));
    }

    @Test
    public void checkUserByIdWithoutErrors() {
        doReturn(TOKEN_VALUE).when(httpServletRequest).getHeader(AUTH_HEADER_NAME);
        doReturn(ID).when(tokenHandler).getUserIdFromToken(TOKEN_VALUE);

        assertTrue(tokenService.checkUser(httpServletRequest, ID));
    }

    @Test
    public void checkUserByIdWhenIdDoesNotMatch() {
        long actualId = 3;

        doReturn(TOKEN_VALUE).when(httpServletRequest).getHeader(AUTH_HEADER_NAME);
        doReturn(ID).when(tokenHandler).getUserIdFromToken(TOKEN_VALUE);

        assertFalse(tokenService.checkUser(httpServletRequest, actualId));
    }

    @Test
    public void getUsernameByRequestWithoutErrors() {
        doReturn(TOKEN_VALUE).when(httpServletRequest).getHeader(AUTH_HEADER_NAME);
        doReturn(ID).when(tokenHandler).getUserIdFromToken(TOKEN_VALUE);
        doReturn(EXPECTED_USER).when(userService).loadUserById(ID);

        String actualUsername = tokenService.getUsernameByRequest(httpServletRequest);
        assertEquals(EXPECTED_USERNAME, actualUsername);
    }
}
