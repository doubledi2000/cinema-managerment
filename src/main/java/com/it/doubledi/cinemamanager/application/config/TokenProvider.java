package com.it.doubledi.cinemamanager.application.config;

import com.it.doubledi.cinemamanager._common.model.exception.ResponseException;
import com.it.doubledi.cinemamanager.infrastructure.persistence.entity.UserEntity;
import com.it.doubledi.cinemamanager.infrastructure.persistence.repository.UserEntityRepository;
import com.it.doubledi.cinemamanager.infrastructure.support.errors.NotFoundError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
@AllArgsConstructor
public class TokenProvider {

    private final String SECRET = "doubledi";
    private final long accessTokenExpiresIn = (long)30 * 24 * 60 * 60 * 1000;
    private final UserEntityRepository userEntityRepository;
    private final String USER_ID = "user_id";

    public String createToken(Authentication authentication, String userId) {
        long now = Instant.now().toEpochMilli();
        Date validity = new Date(now + this.accessTokenExpiresIn);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(USER_ID, userId)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setExpiration(validity)
                .compact();
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiresIn))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractSubject(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUserId(String token) {
        String username = this.extractSubject(token);
        UserEntity userEntity = this.userEntityRepository.findUserByUsername(username).orElseThrow(()->new ResponseException(NotFoundError.USER_NOT_FOUND));
        return userEntity.getId();
    }

}
