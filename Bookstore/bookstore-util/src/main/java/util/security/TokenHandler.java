package util.security;

public interface TokenHandler {

    String AUTH_HEADER_NAME = "Auth-Token";

    String getToken(String userId);

    boolean checkToken(String token);

    Integer getUserIdFromToken(String token);
}
