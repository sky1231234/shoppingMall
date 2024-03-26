package com.project.shop.ordersheet.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class OrderSheet {

  private List<OrderSheetItem> itemList;

  private final String ITEM_LIST_IF_EMPTY_MESSAGE = "주문 상품은 최소 1개 이상이여야한다.";

  public OrderSheet(List<OrderSheetItem> itemList){
    validation(itemList);
    this.itemList = itemList;
  }

  public void validation(List<OrderSheetItem> itemList){

    if(itemList.isEmpty()){
      throw new IllegalArgumentException(ITEM_LIST_IF_EMPTY_MESSAGE);
    }
  }

    public void createOrderSheet(List<OrderSheetItem> orderSheetItems) {

    this.itemList = new ArrayList<>();

      for (OrderSheetItem item : orderSheetItems) {
        OrderSheetItem orderSheetItem = new OrderSheetItem(item.getItem(), item.getItemOptionId(),
                item.getOrderQuantity());

        this.itemList.add(orderSheetItem);
      }

    }
}
