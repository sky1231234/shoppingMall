package com.project.shop.user.service;

import com.project.shop.user.domain.User;
import com.project.shop.user.dto.LoginRequest;
import com.project.shop.user.dto.MyInfoEditRequest;
import com.project.shop.user.dto.SignupRequest;
import com.project.shop.user.dto.UserResponse;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final User user;

    //회원가입
    public void signup(SignupRequest signupRequest){
//        userRepository.findById();
    }

    //회원정보 조회
    public UserResponse myInfo(){
        //user부분 수정
        UserResponse userResponse = UserResponse.getUserResponse(user);
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
