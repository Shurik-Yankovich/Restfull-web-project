package com.expexchangeservice.utils.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenHandler implements ITokenHandler {

    private Key key;

    public JwtTokenHandler() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public String generateToken(Integer userId) {
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
    public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        try {
            return Integer.parseInt(userId);
        } catch (Exception e) {
            return null;
        }
    }

//    @Override
//    public Optional<Integer> getUserIdFromToken(String token) {
//        try {
//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
//            Claims body = claimsJws.getBody();
//            return Optional
//                    .ofNullable(body.getId())
//                    .map(Integer::valueOf);
//        } catch (RuntimeException e) {
//            return Optional.empty();
//        }
//    }
}
