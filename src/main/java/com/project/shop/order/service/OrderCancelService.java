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
import com.project.shop.user.domain.User;
import com.project.shop.user.dto.request.PointRequest;
import com.project.shop.user.repository.PointRepository;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCancelService {

    private final UserRepository userRepository ;
    private final PointRepository pointRepository ;
    private final OrderRepository orderRepository ;
    private final OrderItemRepository orderItemRepository ;
    private final PayRepository payRepository ;
    private final PayCancelRepository payCancelRepository ;


    //부분 취소 등록
    public void partCancelCreate(long orderId, OrderPartCancelRequest orderPartCancelRequest){

        var state = orderPartCancelRequest.getPayCancelType();

        //order 주문상태 부분취소
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER"));

        orderRepository.save(order.cancelOrder(state));

        //orderItem itemId별로 상태 부분취소
        orderPartCancelRequest.getItem().stream().map(
                x -> {
                    var orderItem = orderItemRepository.findByItemId(x.getItemId());

                    return orderItemRepository.save(orderItem.cancelOrder(state));
                }
        );

        //pay 상태 부분취소
        Pay pay = payRepository.findByOrderId(orderId);
        payRepository.save(pay.cancelOrder(state));

        //payCancel 등록
        payCancelRepository.save(orderPartCancelRequest.toEntity(order));
    }

    //취소 등록
    public void orderCancelCreate(long orderId, OrderCancelRequest orderCancelRequest){

        var state = OrderType.취소;

        //order 주문상태 취소
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER"));

        orderRepository.save(order.cancelOrder(state));

        //orderItem 상태 취소
        List<OrderItem> orderItem = orderItemRepository.findByOrderId(orderId);

        if(orderItem.isEmpty())
            throw new RuntimeException("NOT_FOUND_ORDER_ITEM");

        //1
        orderItem.stream().map(x -> {
            return orderItemRepository.save(x.cancelOrder(state));
        });

        //2
        for (OrderItem order1 : orderItem) {
            orderItemRepository.save(order1.cancelOrder(state));
        }

        //pay 상태 취소
        Pay pay = payRepository.findByOrderId(orderId);
        payRepository.save(pay.cancelOrder(state));

        //payCancel 등록
        payCancelRepository.save(orderCancelRequest.toEntity(order));

        //사용 취소 포인트 등록

        //userId 받아오기
        long userId = 5;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        Point point = pointRepository.findAllByUserId()

    }


}
