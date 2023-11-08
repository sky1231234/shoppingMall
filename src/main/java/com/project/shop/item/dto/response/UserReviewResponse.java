package com.project.shop.item.dto.response;

import com.project.shop.item.repository.ItemImgMapping;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.user.domain.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserReviewResponse {

    private List<ItemReviewResponse> itemReviewResponseList; //
      public static UserReviewResponse fromEntity(User user){

        var list = user.getReviewList()
                .stream().map(x -> ItemReviewResponse.fromEntity(x))
                .collect(Collectors.toList());

        return UserReviewResponse.builder()
                .itemReviewResponseList(list)
                .build();
    }
}