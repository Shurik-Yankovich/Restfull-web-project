package com.expexchangeservice.service.security;

public interface TokenHandler {

    String AUTH_HEADER_NAME = "Auth-Token";

    String generateToken(String userId);

    boolean checkToken(String token);

    Integer getUserIdFromToken(String token);
}
