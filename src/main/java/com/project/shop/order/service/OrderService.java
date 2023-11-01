package com.project.shop.order.service;

import com.project.shop.user.dto.LoginRequest;
import com.project.shop.user.dto.MyInfoEditRequest;
import com.project.shop.user.dto.SignupRequest;
import com.project.shop.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

//    private final UserRepository userRepository;


    //회원가입
    public void signup(SignupRequest signupRequest){
//        userRepository.findById();
    }

    //회원정보 조회
    public UserResponse myInfo(){
        UserResponse userResponse = UserResponse.userResponse();
        return userResponse;
    }
    //로그인
    public void login(LoginRequest loginRequest){

    }

    //회원정보 수정
    public void edit(MyInfoEditRequest myInfoEditRequest){

    }

    //회원 탈퇴
    public void delete(){

    }
}
