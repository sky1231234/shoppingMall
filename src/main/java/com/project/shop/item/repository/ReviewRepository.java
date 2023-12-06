package com.project.shop.item.repository;


import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByItem(Item item);

    List<Review> findAllByUsers(User user);

}
