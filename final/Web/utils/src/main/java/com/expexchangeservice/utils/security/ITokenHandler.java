package com.expexchangeservice.utils.security;

public interface ITokenHandler {

    String AUTH_HEADER_NAME = "Auth-Token";

    String generateToken(Long userId);

    boolean checkToken(String token);

    Long getUserIdFromToken(String token);
}
