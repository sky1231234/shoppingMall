package com.project.shop.member.domain;

import com.project.shop.member.dto.request.MemberUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Table(name = "member")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE member SET delete_date = now() WHERE id = ?")
@Where(clause = "delete_date is null")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long userId;     //고객번호
    @Column(name = "loginId", nullable = false, unique = true)
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

    @Builder.Default
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private Set<Authority> authorities  = new HashSet<>();

    public Member updateMember(MemberUpdateRequest memberUpdateRequest){
        this.password = memberUpdateRequest.password();
        this.name = memberUpdateRequest.name();
        this.phoneNum = memberUpdateRequest.phoneNum();
        this.updateDate = LocalDateTime.now();
        return this;
    }

}
