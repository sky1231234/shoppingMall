package com.project.shop.user.controller;


import com.project.shop.user.dto.request.LoginRequest;
import com.project.shop.user.dto.request.UserUpdateRequest;
import com.project.shop.user.dto.request.SignupRequest;
import com.project.shop.user.dto.response.UserResponse;
import com.project.shop.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    //회원가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignupRequest signup){
        authService.signup(signup);
    }

    //회원정보 조회
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse userDetailFind(@RequestBody long userId){
        return authService.userDetailFind(userId);
    }

    //회원정보 수정
    @PutMapping("/{id}/edit")
    @ResponseStatus(HttpStatus.OK)
    public void userUpdate(@RequestBody UserUpdateRequest userUpdateRequest){
        authService.update(userUpdateRequest);
    }

    //회원정보 탈퇴
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void userDelete(long userId){
        authService.delete(userId);
    }

    //로그인
    @PostMapping("user/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginRequest loginRequest){
        authService.login(loginRequest);
    }

}
