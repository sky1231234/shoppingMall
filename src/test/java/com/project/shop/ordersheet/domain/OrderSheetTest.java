package com.project.shop.ordersheet.domain;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.fixture.CategoryFixture;
import com.project.shop.item.fixture.ItemFixture;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderSheetTest {

    private long 옵션번호_1 = 1L;
    private long 옵션번호_2 = 2L;
    private int 주문수량_1 = 2;
    private int 주문수량_2 = 3;

    String 주문상품갯수_예외메시지 = "주문 상품은 최소 1개 이상이여야한다.";


    @DisplayName("주문서 생성 테스트")
    @Test
    public void create(){

        Category 카테고리 = CategoryFixture.createCategoryFixture();
        Item 상품 = ItemFixture.createItemFixture(카테고리);

        OrderSheetItem 첫번째_주문상품 = new OrderSheetItem(상품, 옵션번호_1, 주문수량_1);
        OrderSheetItem 두번째_주문상품 = new OrderSheetItem(상품, 옵션번호_2, 주문수량_2);

        List<OrderSheetItem> 상품리스트 = Arrays.asList(첫번째_주문상품,두번째_주문상품);
        OrderSheet orderSheet = new OrderSheet(상품리스트);

        assertThat(orderSheet).isInstanceOf(OrderSheet.class);
    }

    @DisplayName("생성 테스트 - 주문 상품 갯수(1개 이상)")
    @Test
    public void create_error_itemList(){

        List<OrderSheetItem> 빈_상품리스트 = Collections.emptyList();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderSheet(빈_상품리스트));

        assertThat(exception.getMessage()).isEqualTo(주문상품갯수_예외메시지);

    }

    @DisplayName("주문서 등록 테스트")
    @Test
    public void createOrderSheet(){

        int 상품리스트_1 = 0;
        int 상품리스트_2 = 1;

        Category 카테고리 = CategoryFixture.createCategoryFixture();
        Item 상품 = ItemFixture.createItemFixture(카테고리);

        OrderSheetItem 첫번째_주문상품 = new OrderSheetItem(상품, 옵션번호_1, 주문수량_1);
        OrderSheetItem 두번째_주문상품 = new OrderSheetItem(상품, 옵션번호_2, 주문수량_2);

        List<OrderSheetItem> 상품리스트 = Arrays.asList(첫번째_주문상품,두번째_주문상품);
        OrderSheet orderSheet = new OrderSheet(상품리스트);


        orderSheet.createOrderSheet(상품리스트);


        assertEquals(상품리스트.size(), orderSheet.getItemList().size());

        assertEquals(상품리스트.get(상품리스트_1).getItem(), 상품);
        assertEquals(상품리스트.get(상품리스트_1).getItemOptionId(), 옵션번호_1);
        assertEquals(상품리스트.get(상품리스트_1).getOrderQuantity(), 주문수량_1);

        assertEquals(상품리스트.get(상품리스트_2).getItem(), 상품);
        assertEquals(상품리스트.get(상품리스트_2).getItemOptionId(), 옵션번호_2);
        assertEquals(상품리스트.get(상품리스트_2).getOrderQuantity(), 주문수량_2);
    }

    @DisplayName("주문서 등록 테스트_상품 리스트 갯수 1개 이상")
    @Test
    public void createOrderSheet_error_orderQuantity(){

        List<OrderSheetItem> 빈_상품리스트 = Collections.emptyList();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderSheet(빈_상품리스트).createOrderSheet(빈_상품리스트)
        );
        assertThat(exception.getMessage()).isEqualTo(주문상품갯수_예외메시지);

    }

}
