package com.project.shop.member.repository;

import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByMember(Member member);
}
