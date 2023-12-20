package com.project.shop.user.service;

import com.project.shop.user.domain.Cart;
import com.project.shop.user.domain.User;
import com.project.shop.user.dto.request.LoginRequest;
import com.project.shop.user.dto.request.SignUpRequest;
import com.project.shop.user.dto.request.UserUpdateRequest;
import com.project.shop.user.dto.response.UserResponse;
import com.project.shop.user.repository.CartRepository;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
//    private final PasswordEncoder passwordEncoder;

    //회원가입
    public long signUp(SignUpRequest signUpRequest){

        //loginId 중복 확인
        if(userRepository.findByLoginId(signUpRequest.loginId()).isPresent())
            throw new RuntimeException("ALREADY_EXIST_LOGIN_ID");

        var user = userRepository.save(signUpRequest.toEntity());

        return user.getUserId();
    }

    //로그인
    public void login(LoginRequest loginRequest){
        User user = userRepository.findByLoginId(loginRequest.loginId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

//        if(!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
//            throw new RuntimeException("NOT_MATCH_PASSWORD");
//
//        }
    }

    //내 정보 조회
    public UserResponse userDetailFind(long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_USER"));

        return UserResponse.fromEntity(user);
    }


    //회원 정보 수정
    public void update(long userId, UserUpdateRequest userUpdateRequest){

        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_USER"));

        User update = user.updateUser(userUpdateRequest);
        userRepository.save(update);
    }

    //회원 탈퇴
    public void delete(long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_USER"));

        User delete = user.deleteUser();
        userRepository.save(delete);

        //장바구니 삭제
        List<Cart> cart = cartRepository.findByUsers(user);
        cartRepository.deleteAll(cart);


    }

}
