package com.project.shop.order.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.repository.AuthorityRepository;
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
import com.project.shop.member.domain.PointType;
import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.PointRepository;
import com.project.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final ItemRepository itemRepository ;
    private final ItemImgRepository itemImgRepository ;

    private final OrderRepository orderRepository ;
    private final OrderItemRepository orderItemRepository ;
    private final OptionRepository optionRepository ;
    private final PayRepository payRepository ;

    private final PayCancelRepository payCancelRepository ;
    private final PointRepository pointRepository ;

    //주문내역 전체 조회
    public List<OrderResponse> findAll(String loginId){

        authCheck(loginId);

        List<Order> orderList = orderRepository.findAll();

        if(orderList.isEmpty())
            throw new RuntimeException("NOT_FOUND_ORDER");

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

    //회원별 주문내역 조회
    public OrderUserResponse findAllByMember(String loginId){

        Member member = findLoginMember(loginId);

        List<Order> orderList = orderRepository.findAllByMember(member);

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
                .userId(member.getUserId())
                .id(member.getLoginId())
                .order(list)
                .build();

    }

    //주문내역 상세 조회
    public OrderDetailResponse detailFind(String loginId, long orderId){

        Member member = findLoginMember(loginId);
        Order order = orderFindById(orderId);
        equalLoginMemberCheck(member,order);

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
    @Transactional
    public long create(String loginId, OrderRequest orderRequest){

        Member member = findLoginMember(loginId);

        //1. 주문 상품 있는지 확인
        orderRequest.orderItemRequestList()
                .stream()
                .map(x -> {
                            return itemRepository.findById(x.itemId())
                                    .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_ITEM"));
                        }
                );

        //2. 보유 포인트 확인
        var sumPoint = pointRepository.findSumPoint(member.getUserId());
        if ( sumPoint < orderRequest.usedPoint())
            throw new RuntimeException("NOT_USE_ENOUGH_POINT");

        //3. 상품 가격 확인
        var totalPrice = orderRequest.orderItemRequestList().stream()
                            .mapToInt(x -> x.itemPrice() * x.itemCount())
                            .sum();

        if(orderRequest.orderTotalPrice() != totalPrice)
            throw new RuntimeException("NOT_EQUAL_ITEM_PRICE");

        //4. 주문 금액, 결제 금액 확인
        if(orderRequest.orderTotalPrice() != orderRequest.payPrice() + orderRequest.usedPoint() + orderRequest.deliverFee())
            throw new RuntimeException("NOT_EQUAL_PRICE_PAY_AND_ITEM");

        //order
        //주문번호 랜덤 생성
        String orderNum = new SimpleDateFormat("yyMMdd").format(new Date()) + "_" +
                String.valueOf((Math.random() * 89999) + 10000);
        var order = orderRequest.orderToEntity(member,orderNum, OrderType.완료);
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
            var point = orderRequest.pointToEntity(member);
            pointRepository.save(point);

        }
        return orderResult.getOrderId();
    }

    //주문 수정
    @Transactional
    public void update(String loginId, long orderId, OrderUpdateRequest orderUpdateRequest){

        Member member = findLoginMember(loginId);

        //order
        Order order = orderFindById(orderId);
        equalLoginMemberCheck(member,order);

        orderRepository.save(order.updateOrder(orderUpdateRequest));

        //orderItem
        orderItemRepository.deleteAllByOrder(order);

        var result = orderUpdateRequest.orderItemRequestList()
                .stream()
                .map(x -> {

                    var item = itemRepository.findById(x.itemId())
                                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

                        var option = optionRepository.findByItemAndColorAndSize(item,x.itemColor(),x.itemSize())
                                .orElseThrow(() -> new RuntimeException("NOT_FOUND_OPTION"));

                    return x.toEntity(item,order,option);
        }).toList();

        orderItemRepository.saveAll(result);

        //pay
        Pay pay = payRepository.findByOrder(order);
        payRepository.save(pay.updatePay(orderUpdateRequest));

    }

    //부분취소, 취소 등록
    @Transactional
    public long cancelCreate(String loginId, long orderId, OrderCancelRequest orderCancelRequest){

        OrderType orderType;
        OrderItemType orderItemType = OrderItemType.취소;
        PointType pointType = PointType.사용취소;

        Member member = findLoginMember(loginId);
        Order order = orderFindById(orderId);
        equalLoginMemberCheck(member,order);

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

        //orderItem 상태 변경
        List<OrderItem> orderItems = orderCancelRequest.item().stream()
                .map( x-> {
                    Item item = itemRepository.findById(x)
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

                    OrderItem orderItem = orderItemRepository.findByItemAndOrder(item,order)
                                    .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDERITEM"));

                    return orderItem.cancelOrderItem(orderItemType);
                }
        ).toList();

        orderItemRepository.saveAll(orderItems);

        //payCancel 등록
        var payCancelEntity = orderCancelRequest.payCancelToEntity(orderCancel);
        payCancelRepository.save(payCancelEntity);

        //취소 상태일 때만 취소 포인트 등록
        if(orderType.equals(OrderType.취소)){
            var point = orderCancelRequest.pointToEntity(member,order.getPoint(),pointType);
            pointRepository.save(point);
        }

        return orderCancel.getOrderId();
    }

    //로그인 member 확인
    private Member findLoginMember(String loginId){

        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
    }

    //order 확인
    private Order orderFindById(long orderId){

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER"));
    }

    //로그인 member와 order member 비교
    private void equalLoginMemberCheck(Member member, Order order){
        if( ! member.equals(order.getMember()) )
            throw new RuntimeException("NOT_EQUAL_ORDER_MEMBER");
    }

    //admin 권한 확인
    private void authCheck(String loginId){

        Member member = findLoginMember(loginId);
        Authority authority = authorityRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_AUTH"));;

        if(authority.getAuthName().equals("user"))
            throw new RuntimeException("ONLY_ADMIN");
    }
}
