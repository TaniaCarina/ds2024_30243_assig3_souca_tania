package ro.tuc.ds2020.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("YXJlSHkHjS9FjNsYwYmPnKbFgVcLdMxTnWlRkQ=")
    private String signingKey;

    public Claims getAllClaims(String token) throws SignatureException {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = getAllClaims(token);
        List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean isTokenValid(String token) {
        try {
            getAllClaims(token);
            System.out.println("Token successfully validated.");
            return true;
        } catch (SignatureException e) {
            System.err.println("Invalid token signature.");
        } catch (Exception e) {
            System.err.println("Token validation failed: " + e.getMessage());
        }
        System.out.println("Validation unsuccessful.");
        return false;
    }
}
