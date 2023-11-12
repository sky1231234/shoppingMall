package com.project.shop.item.repository;


import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryNameAndBrandName(String categoryName, String brandName);
}
