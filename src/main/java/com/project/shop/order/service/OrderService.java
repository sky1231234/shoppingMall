package com.project.shop.order.service;

import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.OrderItem;
import com.project.shop.order.domain.Pay;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.order.dto.response.OrderDetailResponse;
import com.project.shop.order.dto.response.OrderItemResponse;
import com.project.shop.order.dto.response.OrderResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.repository.OrderItemRepository;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.order.repository.PayRepository;
import com.project.shop.user.domain.User;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository ;
    private final ItemRepository itemRepository ;

    private final OrderRepository orderRepository ;
    private final OrderItemRepository orderItemRepository ;
    private final OptionRepository optionRepository ;
    private final PayRepository payRepository ;

    //주문내역 조회
    public List<OrderResponse> orderFindAll(){

        //회원번호 받아오기
        long userId = 5;

        List<Order> orderList = orderRepository.findByUserId(userId);

        if(orderList.isEmpty()){
            throw new RuntimeException("NOT_FOUND_ORDER");
        }

        return orderList.stream()
                .map( x -> {

                    List<OrderItem> orderItemList = orderItemRepository.findByOrderId(x.getOrderId());

                    if(orderItemList.isEmpty()){
                        throw new RuntimeException("NOT_FOUND_ORDER_ITEM");
                    }

                    var list = orderItemList.stream()
                            .map( y -> {
                                var item = y.getItem();

                                Option option = optionRepository.findById(y.getItemOptionId())
                                        .orElseThrow(() -> new RuntimeException("NOT_FOUND_OPTION"));

                                var thumbnail = item.getItemImgList().stream()
                                        .filter(z -> z.getItemImgType() == ItemImgType.Y)
                                        .map(z -> {
                                            return OrderResponse.Thumbnail.builder()
                                                    .imgId(z.getItemImgId())
                                                    .url(z.getImgUrl())
                                                    .build();
                                        })
                                        .findFirst().orElse(null);

                                return OrderResponse.OrderItem.builder()
                                        .itemId(item.getItemId())
                                        .itemName(item.getItemName())
                                        .itemCount(y.getTotalQuantity())
                                        .itemSize(option.getSize())
                                        .itemColor(option.getColor())
                                        .itemPrice(item.getPrice())
                                        .itemThumbnail(thumbnail)
                                        .build();
                            })
                            .toList();

                    return OrderResponse.builder()
                            .orderId(x.getOrderId())
                            .orderDate(x.getInsertDate())
                            .orderTotalPrice(x.getPrice())
                            .orderState(x.getOrderType())
                            .deliverFee(x.getDeliverFee())
                            .orderItem(list)
                            .build();
                }).toList();

    }

    //주문내역 회원별 조회
    public OrderUserResponse orderFindByUser(long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        List<Order> orderList = orderRepository.findByUserId(userId);

        if(orderList.isEmpty()){
            throw new RuntimeException("NOT_FOUND_ORDER");
        }
        var list = orderList.stream()
                .map(x -> {
                        return OrderUserResponse.Order.builder()
                                .orderId(x.getOrderId())
                                .orderNum(x.getOrderNum())
                                .orderDate(x.getInsertDate())
                                .orderTotalPrice(x.getPrice())
                                .orderState(x.getOrderType())
                                .deliverFee(x.getDeliverFee())
                                .build();
                    }).toList();

        return OrderUserResponse.builder()
                .userId(userId)
                .id(user.getLoginId())
                .order(list)
                .build();

    }

    //주문내역 상품별 조회
//    public OrderItemResponse orderFindByItem(long orderId){
//    }

    //주문내역 상세 조회
    public OrderDetailResponse orderDetailFind(long orderId){

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER"));

        List<OrderItem> orderItem = orderItemRepository.findByOrderId(orderId);

        if(orderItem.isEmpty())
            throw new RuntimeException("NOT_FOUND_ORDER_ITEM");

        var orderItemList = orderItem.stream()
                .map( x -> {
                    var item = x.getItem();

                    Option option = optionRepository.findById(x.getItemOptionId())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_OPTION"));

                    var thumbnail = item.getItemImgList().stream()
                            .filter(z -> z.getItemImgType() == ItemImgType.Y)
                            .map(z -> {
                                return OrderDetailResponse.Thumbnail.builder()
                                        .imgId(z.getItemImgId())
                                        .url(z.getImgUrl())
                                        .build();
                            })
                            .findFirst().orElse(null);

                    return OrderDetailResponse.OrderItem.builder()
                            .itemId(item.getItemId())
                            .itemName(item.getItemName())
                            .itemCount(x.getTotalQuantity())
                            .itemSize(option.getSize())
                            .itemColor(option.getColor())
                            .itemPrice(item.getPrice())
                            .itemThumbnail(thumbnail)
                            .build();
                }).toList();

        Pay pay = payRepository.findByOrderId(orderId);

        var payData = OrderDetailResponse.Pay.builder()
                .payId(pay.getPayId())
                .usedPoint(pay.getUsedPoint())
                .payCompany(pay.getPayCompany())
                .cardNum(pay.getCardNum())
                .payPrice(pay.getPayPrice())
                .build();

        return OrderDetailResponse.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getInsertDate())
                .orderTotalPrice(order.getPrice())
                .orderState(order.getOrderType())
                .deliverFee(order.getDeliverFee())
                .receiverName(order.getReceiverName())
                .zipcode(order.getZipcode())
                .address(order.getAddress())
                .addressDetail(order.getAddrDetail())
                .receiverPhoneNum(order.getPhoneNum())
                .orderItem(orderItemList)
                .pay(payData)
                .build();
    }

    //주문 등록
    public void create(OrderRequest orderRequest){

        //주문상품 있는지 확인

        //order
        var order = orderRequest.orderToEntity();
        orderRepository.save(order);

        //orderItem
        orderRequest.getOrderItemRequestList().stream()
                .map(x -> {
                    var item = itemRepository.findById(x.getItemId())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));
                    var option = optionRepository.findByColorAndSize(x.getItemColor(),x.getItemSize())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_OPTION"));

                    //여기 order???
                    var orderItem =  x.toEntity(item,order,option);
                    return orderItemRepository.save(orderItem);
                });
        //pay
        payRepository.save(orderRequest.payToEntity(order));

    }

    //주문 수정
    public void update(Long orderId, OrderUpdateRequest orderUpdateRequest){

        //order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER"));

        orderRepository.save(order.updateOrder(orderUpdateRequest));

        //orderItem
        //주문 수정 상품 비교
//        orderUpdateRequest.getOrderItemRequestList().stream()
//                .map(x -> {
//                    x.getItemId()
//            return
//        });

        //pay
        Pay pay = payRepository.findByOrderId(orderId);
        payRepository.save(pay.updatePay(orderUpdateRequest));

    }

}
