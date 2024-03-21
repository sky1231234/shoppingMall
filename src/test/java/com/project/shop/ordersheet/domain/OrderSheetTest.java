package com.project.shop.ordersheet.domain;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderSheetTest {
    private LocalDateTime dateTime = LocalDateTime.now();

    @DisplayName("주문서 생성 테스트")
    @Test
    public void create(){

        Category 카테고리 = new Category("운동화", "나이키",dateTime);
        Item 상품 = new Item(카테고리, "조던", 100000,"한정판매",dateTime);

        OrderSheetItem 첫번째_주문상품 = new OrderSheetItem(상품, 1L, 10000,2);
        OrderSheetItem 두번째_주문상품 = new OrderSheetItem(상품, 2L, 4000, 3);

        List<OrderSheetItem> 상품리스트 = Arrays.asList(첫번째_주문상품,두번째_주문상품);
        OrderSheet orderSheet = new OrderSheet(상품리스트);

        assertThat(orderSheet).isInstanceOf(OrderSheet.class);
    }

    @DisplayName("생성 테스트 - 주문 상품 갯수(1개 이상)")
    @Test
    public void create_error_itemList(){

        List<OrderSheetItem> 상품리스트 = Collections.emptyList();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderSheet(상품리스트));

        assertThat(exception.getMessage()).isEqualTo("주문 상품은 최소 1개 이상이여야한다.");

    }

    @DisplayName("주문서 등록 테스트")
    @Test
    public void createOrderSheet(){

        Category 카테고리 = new Category("운동화", "나이키",dateTime);
        Item 상품 = new Item(카테고리, "조던", 100000,"한정판매",dateTime);

        OrderSheetItem 첫번째_주문상품 = new OrderSheetItem(상품, 1L, 10000,2);
        OrderSheetItem 두번째_주문상품 = new OrderSheetItem(상품, 2L, 4000, 3);

        List<OrderSheetItem> 상품리스트 = Arrays.asList(첫번째_주문상품,두번째_주문상품);
        OrderSheet orderSheet = new OrderSheet(상품리스트);

        orderSheet.createOrderSheet(상품리스트);

        assertEquals(상품리스트.size(), orderSheet.getItemList().size());
        assertEquals(상품리스트.get(1).getOrderQuantity(), 3);
    }

    @DisplayName("주문서 등록 테스트_상품 리스트 갯수 1개 이상")
    @Test
    public void createOrderSheet_error_orderQuantity(){

        List<OrderSheetItem> 상품리스트 = Collections.emptyList();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderSheet(상품리스트).createOrderSheet(상품리스트)
        );
        assertThat(exception.getMessage()).isEqualTo("주문 상품은 최소 1개 이상이여야한다.");

    }

}
