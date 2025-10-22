package pgno51.landlink.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final SecretKey key = Keys.hmacShaKeyFor(
                    """
                    NTBmZjllZGFkMWJmZDg5ODYwYzU5NDUwMjEyNDlmZDcwOWE3N2QzNmU2ZTEzMTQ3NzY2NzhjMzUz
                    ZjA4NzkzZDdjNWRlYjE0ODg3ZDk4Mjc2YzcyZmMwODQ1ZGJlZWVlNzcyNTVhMGE3YzRjNjIwYWY5
                    YjVkZmQ5Yjk5YmMwYWU1YTUyYzRkYjY3ODRkMzYyZDU1NWFhZmM4YWZkNWQ0NmE5NmM0ZTE4NTUy
                    YjhiMzY0OWJhMzkyMTBiOTU2ZmNjZGM3MDMwYWRhOTNmZGUxNTU2YjU1ODkxZDA4MmUyYzZjOGJj
                    YmM5YzIzOWI3ZmE4YzdkNWVjYjI3NzhlMmZjNQ==
                    """.getBytes()
    );

    public String generate(String username, List<String> roles) {
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(key)
                .compact();
    }

    public String validateAndGetUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

        } catch (JwtException e) {
            IO.println(e.getMessage());
        }
        return null;
    }
}
