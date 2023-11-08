package com.project.shop.user.domain;

import com.project.shop.item.domain.Option;
import com.project.shop.item.domain.Review;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;     //고객번호
    @Column(name = "loginId", nullable = false)
    private String loginId;     //아이디
    @Column(name = "password", nullable = false)
    private String password;    //비밀번호
    @Column(name = "name", nullable = false)
    private String name;    //이름
    @Column(name = "address", nullable = false)
    private String address;     //주소
    @Column(name = "addrDetail", nullable = false)
    private String addrDetail;      //상세주소
    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;    //전화번호


    @OneToMany(mappedBy = "users")
    private List<Review> reviewList = new ArrayList<>();

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //가입일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //수정일
    @Column(name = "deleteDate")
    private LocalDateTime deleteDate;   //탈퇴일
}
