package com.expexchangeservice.utils.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenHandler implements ITokenHandler {

    private Key key;

    public JwtTokenHandler() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public String generateToken(Long userId) {
        LocalDateTime expires = LocalDateTime.now().plusDays(7);
        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(Date.from(expires.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key).compact();
    }

    @Override
    public boolean checkToken(String token) {
        if (token != null && !token.equals("")) {
            try {
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                return true;
            } catch (JwtException e) {
                return false;
            }
        } else return false;
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        try {
            return Long.parseLong(userId);
        } catch (Exception e) {
            return null;
        }
    }
}
