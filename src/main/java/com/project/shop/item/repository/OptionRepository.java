package com.project.shop.item.repository;


import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    Optional<Option> findByItemAndColorAndSize(Item item, String color, String size);
}
