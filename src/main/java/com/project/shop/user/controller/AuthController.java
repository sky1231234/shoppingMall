package com.project.shop.user.controller;


import com.project.shop.user.dto.request.LoginRequest;
import com.project.shop.user.dto.request.SignUpRequest;
import com.project.shop.user.dto.request.UserUpdateRequest;
import com.project.shop.user.dto.response.UserResponse;
import com.project.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignUpRequest signUpRequest){
        userService.signUp(signUpRequest);
    }

    //로그인
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginRequest loginRequest){
        userService.login(loginRequest);
    }


}
