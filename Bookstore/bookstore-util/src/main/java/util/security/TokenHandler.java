package util.security;

public interface TokenHandler {

    void regenerateKey();

    String getToken(String json);

    boolean checkToken(String token);

    Integer getUserIdFromToken(String token);
}
