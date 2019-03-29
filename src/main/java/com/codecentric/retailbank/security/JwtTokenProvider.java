package com.codecentric.retailbank.security;

import com.codecentric.retailbank.configuration.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    private AppProperties appProperties;

    public JwtTokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getJwtExpirationInMs());

        Map<String, Object> jwtClaims = new HashMap<>();
        jwtClaims.put("id", userPrincipal.getId());
        jwtClaims.put("email", userPrincipal.getEmail());
        jwtClaims.put("roles", userPrincipal.getAuthorities().stream().map(authority -> ((GrantedAuthority) authority).getAuthority()).collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(jwtClaims)
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getJwtSecret())
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getJwtSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getJwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature.");
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token.");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token.");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token.");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
        }

        return false;
    }

}
