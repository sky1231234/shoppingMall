package com.project.shop.user.repository;

import com.project.shop.user.domain.Address;
import com.project.shop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUsers(User user);
}
