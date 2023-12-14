package com.project.shop.item.repository;


import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.response.CategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryNameAndBrandName(String categoryName, String brandName);


//    @Query(nativeQuery = true , value =
//            "SELECT "
//                    + "category_id , brand_name "
//                    + "FROM category "
//                    + "WHERE category_name = :categoryName"
//    )
//    List<CategoryResponse.BrandList> findBrand(String categoryName);
}
