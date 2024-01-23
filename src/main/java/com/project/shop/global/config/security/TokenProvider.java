package com.project.shop.global.config.security;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import com.project.shop.global.config.security.domain.TokenResponse;
import com.project.shop.global.config.security.domain.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Slf4j
@Configuration
public class TokenProvider {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    long tokenValidityInMilliseconds = 1000L * 60 * 60 * 24 * 8;

    private Key key;

    public TokenProvider(
            @Value(value = "${jwt.secret}") String secret
    ){
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

//  token 생성
    public TokenResponse createToken(Authentication authentication){

        var authorities = authentication.getAuthorities()
                .stream()
                .map((x) -> x.getAuthority())
                .collect(Collectors.joining(","));

        var now = new Date();
        var accessTokenExpireIn = new Date(now.getTime() + tokenValidityInMilliseconds);
        var principal = (UserDto) authentication.getPrincipal();
        var member = principal.getLoginId();
//        authentication.getName()
        String token = Jwts.builder()
                .setSubject("authorization")
                .claim("loginId", member)
                .claim("auth",authorities)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpireIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return new TokenResponse("Bearer", token);
    }

//  인증 정보 조회
    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        var auth = claims.get("auth", String.class);
        var memberId = claims.get("loginId", String.class);

        var authorities = Arrays.stream(auth.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .map(authority -> (GrantedAuthority) authority)
                        .collect(Collectors.toList());

        User principal = new UserDto(memberId, claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

// token 유효성 검증
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
