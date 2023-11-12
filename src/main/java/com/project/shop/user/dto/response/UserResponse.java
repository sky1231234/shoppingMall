package com.project.shop.user.dto.response;

import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.response.ReviewImgResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.user.domain.User;
import lombok.*;

import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse{

    private String loginId;
    private String password;
    private String name;
    private String phoneNum;

    public static UserResponse fromEntity(User user){

        return UserResponse.builder()
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .name(user.getName())
                .phoneNum(user.getPhoneNum())
                .build();
    }
}