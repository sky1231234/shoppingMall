package com.project.shop.ordersheet.domain;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.fixture.CategoryFixture;
import com.project.shop.item.fixture.ItemFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderSheetItemTest {

    String 주문수량_예외메시지 = "상품의 주문 수량은 1개 이상이여야한다.";

    @DisplayName("주문서 아이템 생성 테스트")
    @Test
    public void create(){

        Category 카테고리 = CategoryFixture.createCategoryFixture();
        Item 상품 = ItemFixture.createItemFixture(카테고리);

        long 상품옵션번호 = 1L;
        int 주문수량 = 2;

        OrderSheetItem orderSheetItem = new OrderSheetItem(상품, 상품옵션번호, 주문수량);

        assertThat(orderSheetItem).isInstanceOf(OrderSheetItem.class);
    }

    @DisplayName("생성 테스트 - 주문 수량(1개 이상)")
    @Test
    public void create_error_orderQuantity(){

        Category 카테고리 = CategoryFixture.createCategoryFixture();
        Item 상품 = ItemFixture.createItemFixture(카테고리);
        long 상품옵션번호 = 1L;
        int 최소주문수량_미만수량 = 0;

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderSheetItem(상품, 상품옵션번호, 최소주문수량_미만수량));

        assertThat(exception.getMessage()).isEqualTo(주문수량_예외메시지);

    }

}
