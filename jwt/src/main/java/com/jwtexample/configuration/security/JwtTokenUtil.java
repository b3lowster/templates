package com.jwtexample.configuration.security;

import com.jwtexample.configuration.security.jwt.SigningKeyResolverImpl;
import com.jwtexample.model.dto.UserTokenDetails;
import com.jwtexample.model.types.TokenType;
import com.jwtexample.util.FileUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenUtil {
    @Value("${keys.auth}")
    private String keyName;

    @Value("${app.token.access.in.minutes}")
    private long accessExpirationMinutes;

    @Value("${app.token.refresh.in.minutes}")
    private long refreshExpirationMinutes;

    private final static String JWT_ISSUER = "issuer";
    private final static String NICKNAME = "nickname";
    private final static String REAL_NAME = "name";
    private final static String TOKEN_TYPE = "tokenType";
    private SigningKeyResolver signingKeyResolver;

    @PostConstruct
    public void postConstruct() {
        this.signingKeyResolver = new SigningKeyResolverImpl(keyName);
    }

    public String generateAccessToken(UserTokenDetails tokenDetails) throws IOException {
        Map<String, Object> claims = new HashMap<>();
        claims.put(NICKNAME, tokenDetails.getUserName());
        claims.put(REAL_NAME, String.format("%s %s", tokenDetails.getFirstName(), tokenDetails.getLastName()));
        claims.put(TOKEN_TYPE, TokenType.access);
        return Jwts.builder()
                .setSubject(String.valueOf(tokenDetails.getId()))
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationMinutes * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, FileUtils.getFileContent(keyName))
                .addClaims(claims)
                .compact();
    }

    public String generateRefreshToken(UserTokenDetails tokenDetails) throws IOException {
        Map<String, Object> claims = new HashMap<>();
        claims.put(NICKNAME, tokenDetails.getUserName());
        claims.put(REAL_NAME, String.format("%s %s", tokenDetails.getFirstName(), tokenDetails.getLastName()));
        claims.put(TOKEN_TYPE, TokenType.refresh);
        return Jwts.builder()
                .setSubject(String.valueOf(tokenDetails.getId()))
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMinutes * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, FileUtils.getFileContent(keyName))
                .addClaims(claims)
                .compact();
    }

    public Long getId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKeyResolver(signingKeyResolver)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKeyResolver(signingKeyResolver)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token, TokenType tokenType) {
        try {
            var claimsJws = Jwts.parser()
                    .setSigningKeyResolver(signingKeyResolver)
                    .parseClaimsJws(token);
            return claimsJws.getBody().get(TOKEN_TYPE).equals(tokenType.name());
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
