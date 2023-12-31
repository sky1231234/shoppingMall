package com.project.shop.member.domain;

import com.project.shop.global.config.security.Authority;
import com.project.shop.member.dto.request.MemberUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Table(name = "member")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long userId;     //고객번호
    @Column(name = "loginId", nullable = false)
    private String loginId;     //아이디
    @Column(name = "password", nullable = false)
    private String password;    //비밀번호
    @Column(name = "name", nullable = false)
    private String name;    //이름
    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;    //전화번호

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //가입일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //수정일
    @Column(name = "deleteDate", nullable = true)
    private LocalDateTime deleteDate;   //탈퇴일

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "authName")
    private Set<Authority> authorities;

    public Member updateUser(MemberUpdateRequest memberUpdateRequest){
        this.loginId = memberUpdateRequest.loginId();
        this.password = memberUpdateRequest.password();
        this.name = memberUpdateRequest.name();
        this.phoneNum = memberUpdateRequest.phoneNum();
        this.updateDate = LocalDateTime.now();
        return this;
    }

    public Member deleteUser(){
        this.deleteDate = LocalDateTime.now();
        return this;
    }

}
