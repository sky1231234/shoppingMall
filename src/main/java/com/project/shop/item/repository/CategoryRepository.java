package com.project.shop.item.repository;


import com.project.shop.item.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryNameAndBrandName(String categoryName, String brandName);
    @Query(
            "SELECT "
                    + "brandName, categoryName "
                    + "FROM Category"
                    + "GROUP BY brandName, categoryName"
    )
    Optional<Category> findByCategoryName();
}
