package com.project.shop.user.dto;

import com.project.shop.user.domain.User;
import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

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

//    public static UserResponse userResponse(User user){

        public static UserResponse userResponse(){
            return UserResponse.builder()
                .loginId()
                .password()
                .name()
                .phoneNum()
                .build();
    }
}