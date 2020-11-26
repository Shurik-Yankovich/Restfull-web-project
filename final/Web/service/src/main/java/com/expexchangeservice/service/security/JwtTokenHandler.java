package com.expexchangeservice.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtTokenHandler implements TokenHandler {

    private Key key;

    public JwtTokenHandler() {
//        System.out.println("random key");
//        String keyString = "78";
//        byte[] dec = Base64.getDecoder().decode(keyString);
//        key = new SecretKeySpec(dec, 0, dec.length, "AES");
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public String generateToken(String userId) {
        LocalDateTime expires = LocalDateTime.now().plusDays(7);
        return Jwts.builder()
                .setSubject(userId)
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
    public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        try {
            return Integer.parseInt(userId);
        } catch (Exception e) {
            return null;
        }
    }
}
