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
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignUpRequest signUpRequest){
        userService.signUp(signUpRequest);
    }

    //로그인
    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginRequest loginRequest){
        userService.login(loginRequest);
    }

    //회원정보 조회
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse userDetailFind(@PathVariable("userId") long userId){
        return userService.userDetailFind(userId);
    }

    //회원정보 수정
    @PutMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void userUpdate(@PathVariable("userId") long userId, @RequestBody UserUpdateRequest userUpdateRequest){
        userService.update(userId, userUpdateRequest);
    }

    //회원정보 탈퇴
    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userDelete(@PathVariable("userId") long userId){
        userService.delete(userId);
    }


}
