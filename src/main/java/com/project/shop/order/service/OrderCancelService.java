package com.project.shop.order.service;

import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.order.domain.*;
import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderPartCancelRequest;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.order.dto.response.OrderDetailResponse;
import com.project.shop.order.dto.response.OrderItemResponse;
import com.project.shop.order.dto.response.OrderResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.repository.OrderItemRepository;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.order.repository.PayCancelRepository;
import com.project.shop.order.repository.PayRepository;
import com.project.shop.user.domain.Point;
import com.project.shop.user.domain.PointType;
import com.project.shop.user.domain.User;
import com.project.shop.user.dto.request.PointRequest;
import com.project.shop.user.repository.PointRepository;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCancelService {

    private final UserRepository userRepository ;
    private final ItemRepository itemRepository ;
    private final PointRepository pointRepository ;
    private final OrderRepository orderRepository ;
    private final OrderItemRepository orderItemRepository ;
    private final PayCancelRepository payCancelRepository ;


    //부분 취소 등록
    public void partCancelCreate(long orderId, OrderPartCancelRequest orderPartCancelRequest){

        OrderType orderType;
        PayCancelType payCancelType;
        PointType pointType;
        if(orderPartCancelRequest.payCancelType().equals("부분취소")){
            orderType = OrderType.부분취소;
            payCancelType = PayCancelType.부분취소;
            pointType = PointType.사용취소;
        }else
            throw new RuntimeException("NOT_FOUND_CANCEL");

        //order 주문 상태 취소 등록
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER"));

        var orderEntity = orderPartCancelRequest.cancelToEntity(order, orderType);
        orderRepository.save(orderEntity);

        //orderItem 취소 상품만 등록
        var cancelOrderItem = orderPartCancelRequest.item().stream()
                .map(x -> {
                    var item = itemRepository.findById(x.itemId())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

                    var orderItem = orderItemRepository.findByItemAndOrder(item,order);

                    return OrderItem.builder()
                    .item(item)
                    .order(order)
                    .itemOptionId(orderItem.getItemOptionId())
                    .totalQuantity(orderItem.getTotalQuantity())
                    .totalPrice(orderItem.getTotalPrice())
                    .itemPrice(orderItem.getItemPrice())
                    .build();
                })
                .toList();
        orderItemRepository.saveAll(cancelOrderItem);

        //payCancel 등록
        var payCancelEntity = orderPartCancelRequest.payCancelToEntity(order,payCancelType);
        payCancelRepository.save(payCancelEntity);

        //사용 취소 포인트 등록
        //userId 받아오기
        long userId = 5;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        var point = orderPartCancelRequest.pointToEntity(user,order.getPoint(),pointType);
        pointRepository.save(point);
    }

    //취소 등록
    public void orderCancelCreate(long orderId, OrderCancelRequest orderCancelRequest){

        OrderType orderType;
        PayCancelType payCancelType;
        PointType pointType;
        if(orderCancelRequest.payCancelType().equals("취소")){
            orderType = OrderType.취소;
            payCancelType = PayCancelType.취소;
            pointType = PointType.사용취소;
        }else
            throw new RuntimeException("NOT_FOUND_CANCEL");

        //order 주문 상태 취소 등록
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER"));

        var orderEntity = orderCancelRequest.cancelToEntity(order, orderType);
        orderRepository.save(orderEntity);

        //payCancel 등록
        var payCancelEntity = orderCancelRequest.payCancelToEntity(order,payCancelType);
        payCancelRepository.save(payCancelEntity);

        //사용 취소 포인트 등록
        //userId 받아오기
        long userId = 5;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        var point = orderCancelRequest.pointToEntity(user,order.getPoint(),pointType);
        pointRepository.save(point);

    }


}
