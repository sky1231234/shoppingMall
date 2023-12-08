package com.project.shop.user.domain;

import com.project.shop.item.domain.Option;
import com.project.shop.item.domain.Review;
import com.project.shop.item.domain.ReviewImg;
import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.OrderItem;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>(); //리뷰 이미지

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Cart> cartList = new ArrayList<>(); //주문상품 리스트

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Point> pointList = new ArrayList<>(); //주문상품 리스트

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Address> addressList = new ArrayList<>(); //주문상품 리스트

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //가입일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //수정일
    @Column(name = "deleteDate")
    private LocalDateTime deleteDate;   //탈퇴일
}
