package util.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.Key;

public class JWTTokenHandler implements TokenHandler {

    private Key key;

    @Override
    public void regenerateKey() {

    }

    @Override
    public String getToken(String json) {
        return null;
    }

    @Override
    public boolean checkToken(String token) {
        return false;
    }

    @Override
    public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        String userId =  claims.getSubject();
        try {
            return Integer.parseInt(userId);
        } catch (Exception e){
            return null;
        }
    }
}
