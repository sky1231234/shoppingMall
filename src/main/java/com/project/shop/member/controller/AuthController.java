package com.project.shop.member.controller;

import com.project.shop.global.config.security.domain.TokenResponse;
import com.project.shop.member.dto.request.LoginRequest;
import com.project.shop.member.dto.request.SignUpRequest;
import com.project.shop.member.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Validated
@RequiredArgsConstructor
@Tag( name = "AuthController", description = "[사용자] 회원 관리 API")
public class AuthController {

    private final AuthService authService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(@RequestBody SignUpRequest signUpRequest){
        long signUpId = authService.signUp(signUpRequest);
        return ResponseEntity.created(URI.create("/signup"+signUpId)).build();

    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok()
                .body(authService.login(loginRequest));
    }


}
