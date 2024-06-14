package com.shreeraam.userservice.service;

import com.shreeraam.userservice.model.Role;
import com.shreeraam.userservice.model.User;
import com.shreeraam.userservice.model.UserPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "52e0f9fb552e3793bf64dfbdd685672177b16d96d64bbe8e4a18f91dfbc7f066";

    public String generateToken(User user) {
        UserPayload payload = new UserPayload();
        payload.setFirstName(user.getFirstName());
        payload.setLastName(user.getLastName());
        payload.setRole(user.getRole().name());

        String token = Jwts
                .builder()
                .subject(user.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .claim("payload", payload)
                .signWith(getSigningKey())
                .compact();

        extractUserRole(token);

        return token;
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Role extractUserRole(String token) {
        String role = extractClaim(token, claims -> claims.get("payload", Map.class).get("role")).toString();
        return Role.valueOf(role);
    }

    public boolean isValid(String token, User user) {
        String userId = extractUserId(token);
        return userId.equals(user.getId()) && !isTokenExpired(token);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
