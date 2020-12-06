package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.UserCreds;
import com.expexchangeservice.model.dto.UserDto;
import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Role;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.service.config.UserServiceTestContextConfiguration;
import com.expexchangeservice.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserServiceTestContextConfiguration.class)
public class UserServiceTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static User EXPECTED_USER;
    private static UserDto EXPECTED_USER_DTO;
    private static final long ID = 1L;
    private static final String USERNAME = "user";
    private static final String PASSWORD = "123";
    private static final String CRYPT_PASSWORD = "ytg5ka_qk";

    @BeforeAll
    public static void init() {
        EXPECTED_USER = new User();
        EXPECTED_USER.setId(ID);
        EXPECTED_USER.setUsername(USERNAME);
        EXPECTED_USER.setPassword(CRYPT_PASSWORD);
        EXPECTED_USER.setRole(Role.ROLE_ADMIN);
        EXPECTED_USER_DTO = new UserDto();
        EXPECTED_USER_DTO.setUsername(USERNAME);
        EXPECTED_USER_DTO.setPassword(PASSWORD);
    }

    @Test
    public void loadUserByUsernameWithoutError() {
        doReturn(EXPECTED_USER).when(userRepository).findByUsername(USERNAME);

        User actualUser = userService.loadUserByUsername(USERNAME);
        assertEquals(EXPECTED_USER, actualUser);
    }

    @Test
    public void loadUserByUsernameAndPasswordWithoutError() {
        doReturn(EXPECTED_USER).when(userRepository).findByUsername(USERNAME);
        doReturn(true).when(bCryptPasswordEncoder).matches(PASSWORD, CRYPT_PASSWORD);

        User actualUser = userService.loadUserByUsernameAndPassword(USERNAME, PASSWORD);
        assertEquals(EXPECTED_USER, actualUser);
    }

    @Test
    public void loadUserByUsernameAndPasswordWhenPasswordIsNotRight() {
        doReturn(EXPECTED_USER).when(userRepository).findByUsername(USERNAME);
        doReturn(false).when(bCryptPasswordEncoder).matches(PASSWORD, CRYPT_PASSWORD);

        User actualUser = userService.loadUserByUsernameAndPassword(USERNAME, PASSWORD);
        assertNull(actualUser);
    }

    @Test
    public void loadUserByIdWithoutError() {
        doReturn(EXPECTED_USER).when(userRepository).read(ID);

        User actualUser = userService.loadUserById(ID);
        assertEquals(EXPECTED_USER, actualUser);
    }

    @Test
    public void createUserWithoutError() {
        doReturn(null).when(userRepository).findByUsername(USERNAME);
        doReturn(CRYPT_PASSWORD).when(bCryptPasswordEncoder).encode(PASSWORD);
        doNothing().when(userRepository).create(any(User.class));

        assertTrue(userService.createUser(EXPECTED_USER_DTO));
    }

    @Test
    public void createUserWhenCurrentUsernameIsInDatabase() {
        doReturn(EXPECTED_USER).when(userRepository).findByUsername(USERNAME);

        assertFalse(userService.createUser(EXPECTED_USER_DTO));
    }

    @Test
    public void deleteUserWithoutError() {
        doReturn(EXPECTED_USER).when(userRepository).read(ID);
        doNothing().when(userRepository).delete(EXPECTED_USER);

        assertTrue(userService.deleteUser(ID));
    }

    @Test
    public void deleteUserWhenUserNotInDatabase() {
        doReturn(null).when(userRepository).read(ID);

        assertFalse(userService.deleteUser(ID));
    }

    @Test
    public void changeUserPasswordWithoutError() {
        String actualPassword = "user";
        String actualCryptPassword = "po5ety";
        User actualUser = new User();
        actualUser.setId(ID);
        actualUser.setUsername(USERNAME);
        actualUser.setPassword(actualCryptPassword);
        actualUser.setRole(Role.ROLE_ADMIN);
        UserDto userDto = new UserDto();
        userDto.setUsername(USERNAME);
        userDto.setPassword(actualPassword);
        UserCreds userCreds = new UserCreds();
        userCreds.setUser(userDto);
        userCreds.setNewPassword(PASSWORD);
        userCreds.setPasswordConfirm(PASSWORD);

        doReturn(actualUser).when(userRepository).findByUsername(USERNAME);
        doReturn(true).when(bCryptPasswordEncoder).matches(actualPassword, actualCryptPassword);
        doReturn(CRYPT_PASSWORD).when(bCryptPasswordEncoder).encode(PASSWORD);
        doNothing().when(userRepository).update(any(User.class));

        assertTrue(userService.changeUserPassword(userCreds));
        assertEquals(EXPECTED_USER, actualUser);
    }

    @Test
    public void changeUserPasswordWhenPasswordIsNotRight() {
        UserCreds userCreds = new UserCreds();
        userCreds.setUser(EXPECTED_USER_DTO);
        userCreds.setNewPassword(PASSWORD);
        userCreds.setPasswordConfirm(PASSWORD);

        doReturn(EXPECTED_USER).when(userRepository).findByUsername(USERNAME);
        doReturn(false).when(bCryptPasswordEncoder).matches(PASSWORD, CRYPT_PASSWORD);

        assertFalse(userService.changeUserPassword(userCreds));
    }

    @Test
    public void changeUserRoleWithoutError() {
        boolean isAdmin = true;
        User actualUser = new User();
        actualUser.setId(EXPECTED_USER.getId());
        actualUser.setUsername(EXPECTED_USER.getUsername());
        actualUser.setPassword(EXPECTED_USER.getPassword());
        actualUser.setRole(Role.ROLE_USER);

        doReturn(actualUser).when(userRepository).findByUsername(USERNAME);
        doNothing().when(userRepository).update(any(User.class));

        assertTrue(userService.changeUserRole(USERNAME, isAdmin));
        assertEquals(EXPECTED_USER, actualUser);
    }

    @Test
    public void changeUserRoleWhenUserNotInDatabase() {
        doReturn(null).when(userRepository).findByUsername(USERNAME);

        assertFalse(userService.changeUserRole(USERNAME, true));
    }

}
