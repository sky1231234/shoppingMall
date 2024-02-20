package com.project.shop.member.dto.response;

import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.DeleteType;
import com.project.shop.member.domain.Member;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {

    private String loginId;
    private String name;
    private String phoneNum;
    private String auth;

    public static MemberResponse fromEntity(Member member, Authority authority){

        return MemberResponse.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .phoneNum(member.getPhoneNum())
                .auth(authority.getAuthName())
                .build();
    }

}