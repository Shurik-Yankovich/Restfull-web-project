package com.expexchangeservice.utils.security;

public interface ITokenHandler {

    String AUTH_HEADER_NAME = "Auth-Token";

    String generateToken(String userId);

    boolean checkToken(String token);

    Integer getUserIdFromToken(String token);
}
