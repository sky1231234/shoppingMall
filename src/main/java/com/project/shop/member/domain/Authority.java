package com.project.shop.member.domain;

import lombok.*;

import javax.persistence.*;

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
}
