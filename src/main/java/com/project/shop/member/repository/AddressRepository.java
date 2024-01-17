package com.project.shop.member.repository;

import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByMember(Member member);

}
