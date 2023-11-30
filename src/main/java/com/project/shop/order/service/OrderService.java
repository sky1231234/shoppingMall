package com.project.shop.order.service;

import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Option;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.OrderItem;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.response.OrderItemResponse;
import com.project.shop.order.dto.response.OrderResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.repository.OrderItemRepository;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.user.domain.User;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository ;

    private final OrderRepository orderRepository ;
    private final OrderItemRepository orderItemRepository ;
    private final OptionRepository optionRepository ;

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
    public OrderItemResponse orderFindByItem(long itemId){


    }

    //주문내역 상세 조회
    public OrderResponse orderDetailFind(long orderId){


    }

    //주문 등록
    public void create(OrderRequest orderRequest){

        if(orderRepository.findByOrderNameAndBrandName(orderRequest.getOrderName(),orderRequest.getBrandName()).isPresent()){
            throw new RuntimeException("등록된 주문 있음");
        }

        orderRepository.save(orderRequest.toEntity());

    }

    //주문 수정
    public void update(Long orderId, OrderUpdateRequest orderUpdateRequest){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY_ID"));

        order.updateOrder(orderUpdateRequest);
    }

}
