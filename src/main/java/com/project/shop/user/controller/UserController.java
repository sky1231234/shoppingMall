package com.project.shop.user.controller;


import com.project.shop.user.dto.request.LoginRequest;
import com.project.shop.user.dto.request.UserUpdateRequest;
import com.project.shop.user.dto.request.SignupRequest;
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
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignupRequest signup){
//        authService.signup(signup);
    }

    //회원정보 조회
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse userDetailFind(@RequestBody long userId){
        return userService.userDetailFind(userId);
    }

    //회원정보 수정
    @PutMapping("/{id}/edit")
    @ResponseStatus(HttpStatus.OK)
    public void userUpdate(@RequestBody UserUpdateRequest userUpdateRequest){
//        authService.update(userUpdateRequest);
    }

    //회원정보 탈퇴
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void userDelete(long userId){
        userService.delete(userId);
    }

    //로그인
    @PostMapping("user/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginRequest loginRequest){
        userService.login(loginRequest);
    }

}
