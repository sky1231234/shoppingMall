package com.project.shop.member.repository;

import com.project.shop.item.domain.Option;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByMember(Member member);
}
