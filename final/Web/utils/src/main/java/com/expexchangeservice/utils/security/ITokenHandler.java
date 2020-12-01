package com.expexchangeservice.utils.security;

import java.util.Optional;

public interface ITokenHandler {

    String AUTH_HEADER_NAME = "Auth-Token";

    String generateToken(Integer userId);

    boolean checkToken(String token);

        Integer getUserIdFromToken(String token);
//    Optional<Integer> getUserIdFromToken(String token);
}
