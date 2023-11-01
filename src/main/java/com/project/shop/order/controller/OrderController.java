package com.project.shop.order.controller;


import com.project.shop.user.dto.LoginRequest;
import com.project.shop.user.dto.MyInfoEditRequest;
import com.project.shop.user.dto.SignupRequest;
import com.project.shop.user.dto.UserResponse;
import com.project.shop.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/auth")
@RequiredArgsConstructor
@Validated
public class OrderController {



}
