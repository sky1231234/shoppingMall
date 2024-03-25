package com.project.shop.global.config.security;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.project.shop.global.config.security.domain.TokenResponse;
import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.global.config.security.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@Configuration
public class TokenProvider {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    long tokenValidityInMilliseconds = 1000L * 60 * 60 * 24 * 8;

    private final CustomUserDetailsService customUserDetailsService;

    private Key key;

    public TokenProvider(
            @Value(value = "${jwt.secret}") String secret,
            CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService = customUserDetailsService;
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public TokenResponse createToken(Authentication authentication){
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        var now = new Date();
        var accessTokenExpireIn = new Date(now.getTime() + tokenValidityInMilliseconds);
        var principal = (UserDto) authentication.getPrincipal();
        var member = principal.getLoginId();

        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("loginId", member)
                .claim("auth",authorities)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpireIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return new TokenResponse("Bearer", token);
    }

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        var auth = claims.get("auth", String.class);
        var memberId = claims.get("loginId", String.class);


        List<SimpleGrantedAuthority> authorities = Arrays.stream(auth.split(","))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId);

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            logger.info("잘못된 JWT 서명입니다.");

        }catch(ExpiredJwtException e){
            logger.info("만료된 JWT 토큰입니다.");

        }catch(UnsupportedJwtException e){
            logger.info("지원하지 않는 JWT 토큰입니다.");

        }catch(IllegalArgumentException e){
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
