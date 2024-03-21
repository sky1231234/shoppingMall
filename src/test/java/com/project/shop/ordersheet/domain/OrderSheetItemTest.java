package com.project.shop.ordersheet.domain;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderSheetItemTest {

    private LocalDateTime dateTime = LocalDateTime.now();
    @DisplayName("주문서 아이템 생성 테스트")
    @Test
    public void create(){

        Category 카테고리 = new Category("운동화", "나이키",dateTime);
        Item 상품 = new Item(카테고리, "조던", 100000,"한정판매",dateTime);
        long 상품옵션번호 = 1L;
        int 상품가격 = 120000;
        int 주문수량 = 2;

        OrderSheetItem orderSheetItem = new OrderSheetItem(상품, 상품옵션번호, 상품가격, 주문수량);

        assertThat(orderSheetItem).isInstanceOf(OrderSheetItem.class);
    }

    @DisplayName("생성 테스트 - 상품 가격(0원 초과)")
    @Test
    public void create_error_itemPrice(){

        Category 카테고리 = new Category("운동화", "나이키",dateTime);
        Item 상품 = new Item(카테고리, "조던", 100000,"한정판매",dateTime);
        long 상품옵션번호 = 1L;
        int 상품가격 = 0;
        int 주문수량 = 2;

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderSheetItem(상품, 상품옵션번호, 상품가격, 주문수량));

        assertThat(exception.getMessage()).isEqualTo("상품의 가격은 0원 초과여야한다.");

    }

    @DisplayName("생성 테스트 - 주문 수량(1개 이상)")
    @Test
    public void create_error_orderQuantity(){

        Category 카테고리 = new Category("운동화", "나이키",dateTime);
        Item 상품 = new Item(카테고리, "조던", 100000,"한정판매",dateTime);
        long 상품옵션번호 = 1L;
        int 상품가격 = 120000;
        int 주문수량 = 0;

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderSheetItem(상품, 상품옵션번호, 상품가격, 주문수량));

        assertThat(exception.getMessage()).isEqualTo("상품의 주문 수량은 1개 이상이여야한다.");

    }

    //상품이 존재하는지 확인하려면 db와 확인해야하는데 도메인 테스트의 역할이 아닌 것 같다
//    @DisplayName("생성 테스트 - 상품 존재 확인")
//    @Test
//    public void create_error_item(){
//
//        Category 카테고리 = new Category("운동화", "나이키",dateTime);
//        Item 상품 = new Item(카테고리, "덩크", 200000,"상시판매",dateTime);
//        long 상품옵션번호 = 1L;
//        int 상품가격 = 120000;
//        int 주문수량 = 2;
//
//        assertThrows(RuntimeException.class, () -> {
//            OrderSheetItem orderSheetItem = new OrderSheetItem(상품, 상품옵션번호, 상품가격, 주문수량);
//        }, "NOT_FOUND_ITEM");
//
//    }



}
