package com.project.shop.member.repository;


import com.project.shop.item.domain.Item;
import com.project.shop.member.domain.Cart;
import com.project.shop.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByMember(Member member);
    Optional<Cart> findByMemberAndAndItemAndOptionId(Member member, Item item, long optionId);

}
