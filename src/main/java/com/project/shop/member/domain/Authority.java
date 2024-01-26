package com.project.shop.member.domain;

import com.project.shop.member.dto.request.MemberUpdateRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "authorities")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authId;

    @Column(name = "authName", nullable = false)
    private String authName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public Authority updateAuth(String auth){
        this.authName = auth;
        return this;
    }
}
