package com.project.shop.item.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class CategoryResponse {

//    {
//        "categoryName" : "운동화",
//        "brand" : [
//            {
//                  "brandName" : "나이키",
//                  "categoryId" : 1,
//             }
//         ]
//    }

    private String categoryName;


}