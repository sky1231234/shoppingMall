package com.project.shop.member.controller;

import com.project.shop.global.config.security.domain.TokenResponse;
import com.project.shop.member.dto.request.LoginRequest;
import com.project.shop.member.dto.request.SignUpRequest;
import com.project.shop.member.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
@Tag( name = "AuthController", description = "[사용자] 회원 관리 API")
public class AuthController {

    private final AuthService authService;

    //회원가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignUpRequest signUpRequest){
        authService.signUp(signUpRequest);
    }

    //로그인
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }


}
