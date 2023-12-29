package com.project.shop.member.dto.response;

import com.project.shop.member.domain.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {

    private String loginId;
    private String password;
    private String name;
    private String phoneNum;

    public static MemberResponse fromEntity(Member member){

        return MemberResponse.builder()
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .name(member.getName())
                .phoneNum(member.getPhoneNum())
                .build();
    }
}