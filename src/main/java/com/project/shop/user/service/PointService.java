package com.project.shop.user.service;

import com.project.shop.user.domain.User;
import com.project.shop.user.dto.request.LoginRequest;
import com.project.shop.user.dto.request.SignupRequest;
import com.project.shop.user.dto.request.UserUpdateRequest;
import com.project.shop.user.dto.response.UserResponse;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final User user;
    private final UserRepository userRepository;

    //회원정보 조회
    public UserResponse userDetailFind(long userId){
        var data = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("userID가 없습니다."));

        return UserResponse.fromEntity(data);
    }

    //회원가입
    public void signup(SignupRequest signupRequest){
        userRepository.save();
    }


    //로그인
    public void login(LoginRequest loginRequest){

    }

    //회원정보 수정
    public void update(UserUpdateRequest userUpdateRequest){
        userRepository.save();
    }

    //회원 탈퇴
    public void delete(long userId){
        userRepository.deleteById(userId);
    }
}
