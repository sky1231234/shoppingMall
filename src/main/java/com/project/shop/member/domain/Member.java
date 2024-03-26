package com.project.shop.member.domain;

import com.project.shop.global.config.security.domain.UserRole;
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
    private long userId;
    @Column(name = "loginId", nullable = false, unique = true)
    private String loginId;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;

    @Column(name = "userRole")
    private String userRole;

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;
    @Column(name = "deleteDate")
    private LocalDateTime deleteDate;


    public Member updateMember(MemberUpdateRequest memberUpdateRequest){
        this.password = memberUpdateRequest.password();
        this.name = memberUpdateRequest.name();
        this.phoneNum = memberUpdateRequest.phoneNum();
        this.updateDate = LocalDateTime.now();
        return this;
    }

}
