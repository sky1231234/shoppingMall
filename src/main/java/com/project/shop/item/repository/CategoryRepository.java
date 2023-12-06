package com.project.shop.item.repository;


import com.project.shop.item.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryNameAndBrandName(String categoryName, String brandName);
    @Query(nativeQuery = true , value =
            "SELECT "
                    + "brandName, categoryName "
                    + "FROM Category"
    )
    Optional<Category> findByCategoryName();
}
