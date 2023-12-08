package com.project.shop.item.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private List<BrandList> brand;

        @Builder
        @Getter
        public static class BrandList{
            private String brandName;
            private long categoryId;

    }

}