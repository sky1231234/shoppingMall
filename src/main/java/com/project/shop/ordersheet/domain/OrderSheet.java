package com.project.shop.ordersheet.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderSheet {

  private List<OrderSheetItem> itemList;
  LocalDateTime now = LocalDateTime.now();

  public OrderSheet(List<OrderSheetItem> itemList){
    validation(itemList);
    this.itemList = itemList;
  }

  public void validation(List<OrderSheetItem> itemList){

    if(itemList.isEmpty()){
      throw new IllegalArgumentException("주문 상품은 최소 1개 이상이여야한다.");
    }
  }

    public void createOrderSheet(List<OrderSheetItem> orderSheetItems) {

    this.itemList = new ArrayList<>();

      for (OrderSheetItem item : orderSheetItems) {
        OrderSheetItem orderSheetItem = new OrderSheetItem(item.getItem(), item.getItemOptionId(),
                item.getItemPrice(), item.getOrderQuantity());

        this.itemList.add(orderSheetItem);
      }

    }
}
