package com.anhn.bookapi.security;

import com.anhn.bookapi.aop.LoggingAspect;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private SecretKeySpec getSecretKeySpec() {
        return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(String username, List<String> roleNames) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setSubject(username)
                .claim("roles", roleNames)
                .signWith(getSecretKeySpec())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKeySpec())
                .build();

        return parser
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKeySpec())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            logger.error("JWT expired: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Malformed JWT: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("Illegal argument token: {}", ex.getMessage());
        }

        return false;
    }
}
