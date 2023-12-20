package com.project.shop.order.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.order.domain.*;
import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.order.dto.response.OrderDetailResponse;
import com.project.shop.order.dto.response.OrderResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.repository.OrderItemRepository;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.order.repository.PayCancelRepository;
import com.project.shop.order.repository.PayRepository;
import com.project.shop.user.domain.PointType;
import com.project.shop.user.domain.User;
import com.project.shop.user.repository.PointRepository;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository ;
    private final ItemRepository itemRepository ;
    private final ItemImgRepository itemImgRepository ;

    private final OrderRepository orderRepository ;
    private final OrderItemRepository orderItemRepository ;
    private final OptionRepository optionRepository ;
    private final PayRepository payRepository ;

    private final PayCancelRepository payCancelRepository ;
    private final PointRepository pointRepository ;

    //주문내역 조회
    public List<OrderResponse> orderFindAll(){

        //userId 받아오기
        long userId = 1;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        List<Order> orderList = orderRepository.findByUsers(user);

        if(orderList.isEmpty()){
            throw new RuntimeException("NOT_FOUND_ORDER");
        }

        return orderList.stream()
                .map( x -> {

                    List<OrderItem> orderItemList = orderItemRepository.findByOrder(x);

                    if(orderItemList.isEmpty()){
                        throw new RuntimeException("NOT_FOUND_ORDER_ITEM");
                    }

                    var list = orderItemList.stream()
                            .map( y -> {
                                var item = y.getItem();
                                List<ItemImg> itemImgList = itemImgRepository.findByItem(item);

                                Option option = optionRepository.findById(y.getItemOptionId())
                                        .orElseThrow(() -> new RuntimeException("NOT_FOUND_OPTION"));

                                var thumbnail = itemImgList.stream()
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

        List<Order> orderList = orderRepository.findByUsers(user);

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

    //주문내역 상세 조회
    public OrderDetailResponse orderDetailFind(long orderId){

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER"));

        List<OrderItem> orderItem = orderItemRepository.findByOrder(order);

        if(orderItem.isEmpty())
            throw new RuntimeException("NOT_FOUND_ORDER_ITEM");

        var orderItemList = orderItem.stream()
                .map( x -> {
                    var item = x.getItem();

                    List<ItemImg> itemImgList = itemImgRepository.findByItem(item);

                    Option option = optionRepository.findById(x.getItemOptionId())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_OPTION"));

                    var thumbnail = itemImgList.stream()
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

        Pay pay = payRepository.findByOrder(order);

        var payData = OrderDetailResponse.Pay.builder()
                .payId(pay.getPayId())
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
    public long create(long userId, OrderRequest orderRequest){

        //주문 상품 있는지 확인
        orderRequest.orderItemRequestList().stream().map(x -> {
            return itemRepository.findById(x.itemId())
                    .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_ITEM"));
        });

        //order
        //userId 받아오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        //주문번호 랜덤 생성
        String orderNum = "1223124";
        var order = orderRequest.orderToEntity(user,orderNum, OrderType.완료);
        var orderResult = orderRepository.save(order);

        //orderItem
        var result = orderRequest.orderItemRequestList().stream()
                .map(x -> {
                    var item = itemRepository.findById(x.itemId())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));
                    var option = optionRepository.findByItemAndColorAndSize(item,x.itemColor(),x.itemSize())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_OPTION"));

                    return x.toEntity(item,order,option);
                }).toList();

        orderItemRepository.saveAll(result);

        //pay
        payRepository.save(orderRequest.payToEntity(order));

        //point
        if(orderRequest.usedPoint() != 0){
            var point = orderRequest.pointToEntity(user);
            pointRepository.save(point);

        }
        return orderResult.getOrderId();
    }

    //주문 수정
    public void update(Long orderId, OrderUpdateRequest orderUpdateRequest){

        //order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER"));

        orderRepository.save(order.updateOrder(orderUpdateRequest));

        //orderItem
        var result = orderUpdateRequest.orderItemRequestList()
                .stream()
                .map(x -> {

                    var orderItem = orderItemRepository.findById(x.orderItemId())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_ITEM"));

                        var item = itemRepository.findById(x.itemId())
                                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

                        var option = optionRepository.findByItemAndColorAndSize(item,x.itemColor(),x.itemSize())
                                .orElseThrow(() -> new RuntimeException("NOT_FOUND_OPTION"));

                        return orderItem.updateOrderItem(x, item.getPrice(), option.getOptionId());

        }).toList();

        orderItemRepository.saveAll(result);

        //pay
        Pay pay = payRepository.findByOrder(order);
        payRepository.save(pay.updatePay(orderUpdateRequest));

    }

    //부분취소, 취소 등록
    public long orderCancelCreate(long userId, long orderId, OrderCancelRequest orderCancelRequest){

        //userId 받아오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        OrderType orderType;
        OrderItemType orderItemType = OrderItemType.취소;
        PointType pointType = PointType.사용취소;

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER"));

        List<OrderItem> orderItemList = orderItemRepository.findByOrder(order);

//        취소 요청 갯수 파악해서 취소인지 부분취소인지 확인
        var cancelRequestSize = orderCancelRequest.item().size();
        if(orderItemList.size() == cancelRequestSize)
            orderType = OrderType.취소; //전체 취소
        else
            orderType = OrderType.부분취소; //부분 취소

        //order 상태 변경
        var cancel = order.cancelOrder(orderType);
        var orderCancel = orderRepository.save(cancel);
        List<OrderItem> updateList = new ArrayList<>();

        //orderItem 상태 변경
        List<OrderItem> orderItems = orderCancelRequest.item().stream()
                .map( x-> {
                    Item item = itemRepository.findById(x)
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

                    OrderItem orderItem = orderItemRepository.findByItemAndOrder(item,order)
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_ITEM"));

                    return orderItem.cancelOrderItem(orderItemType);
                }
        ).toList();

        orderItemRepository.saveAll(orderItems);

        //payCancel 등록
        var payCancelEntity = orderCancelRequest.payCancelToEntity(orderCancel);
        payCancelRepository.save(payCancelEntity);

        //사용 취소 포인트 등록
        var point = orderCancelRequest.pointToEntity(user,order.getPoint(),pointType);
        pointRepository.save(point);

        return orderCancel.getOrderId();
    }
}
