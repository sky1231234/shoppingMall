package com.project.shop.order.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.item.builder.CategoryBuilder;
import com.project.shop.item.builder.ItemBuilder;
import com.project.shop.item.domain.*;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.service.AuthService;
import com.project.shop.mock.WithCustomMockUser;
import com.project.shop.order.builder.OrderBuilder;
import com.project.shop.order.domain.*;
import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.response.OrderDetailResponse;
import com.project.shop.order.dto.response.OrderResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.repository.OrderItemRepository;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.order.repository.PayCancelRepository;
import com.project.shop.order.repository.PayRepository;
import com.project.shop.member.builder.PointBuilder;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.PointRepository;
import com.project.shop.member.repository.MemberRepository;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class OrderServiceTest extends ServiceCommon {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    PayRepository payRepository;
    @Autowired
    PointRepository pointRepository;
    //상품 등록
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ItemImgRepository itemImgRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
     OptionRepository optionRepository;
    @Autowired
    PayCancelRepository payCancelRepository;
    @Autowired
    ItemRepository itemRepository;
    Member member1; Member member2;
     Item item1; Item item2;
     ItemImg itemImg1; ItemImg itemImg2; ItemImg itemImg3;

     Option option1; Option option2; Option option3;
     Point point1; Point point2;
    Order order; Order order2;


    @BeforeEach
    public void before(){
        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        member2 = memberBuilder.signUpAdminMember();
        var memberSave = memberRepository.save(member1);
        var adminSave = memberRepository.save(member2);

        //auth
        Authority auth = memberBuilder.auth(memberSave);
        Authority authAdmin = memberBuilder.authAdmin(adminSave);
        authorityRepository.save(auth);
        authorityRepository.save(authAdmin);

        //category
        Category category = CategoryBuilder.createCategory1();
        categoryRepository.save(category);

        //item
        item1 = ItemBuilder.createItem1(category);
        item2 = ItemBuilder.createItem2(category);
        itemRepository.save(item1);
        itemRepository.save(item2);

        //itemImg
        itemImg1 = ItemBuilder.createImg1(item1);
        itemImg2 = ItemBuilder.createImg2(item1);
        itemImg3 = ItemBuilder.createImg3(item2);
        itemImgRepository.save(itemImg1);
        itemImgRepository.save(itemImg2);
        itemImgRepository.save(itemImg3);

        //option
        option1 = ItemBuilder.createOption1(item1);
        option2 = ItemBuilder.createOption2(item1);
        option3 = ItemBuilder.createOption3(item2);
        optionRepository.save(option1);
        optionRepository.save(option2);
        optionRepository.save(option3);

        //point
        point1 = PointBuilder.createPoint1(member1);
        point2 = PointBuilder.createPoint2(member1);
        pointRepository.save(point1);
        pointRepository.save(point2);

        order = OrderBuilder.createOrder(member1);
        order2 = OrderBuilder.createOrder2(member2);
        OrderItem orderItem1 = OrderBuilder.createOrderItem1(item1, order);
        OrderItem orderItem2 = OrderBuilder.createOrderItem2(item1, order2);
        OrderItem orderItem3 = OrderBuilder.createOrderItem2(item2, order);
        Pay pay = OrderBuilder.createPay(order);
        Pay pay2 = OrderBuilder.createPay(order2);

        orderRepository.save(order);
        orderRepository.save(order2);
        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);
        orderItemRepository.save(orderItem3);
        payRepository.save(pay);
        payRepository.save(pay2);
    }

    @Test
    @DisplayName("주문 내역 전체 조회")
    void orderFindAll(){

        //given

        //when
        List<OrderResponse> orderResponses = orderService.orderFindAll(member2.getLoginId());

        //then
        assertThat(orderResponses.size()).isEqualTo(2);
        assertThat(orderResponses.get(0)
                        .getOrderItem().get(0).getItemSize())
                .isEqualTo("220");
        assertThat(orderResponses.get(1)
                .getOrderTotalPrice()).isEqualTo(23200);

    }

    @Test
    @DisplayName("주문 내역 회원별 조회")
    void orderFindByUser(){

        //given

        //when
        OrderUserResponse orderUserResponse = orderService.orderFindByUser(member1.getLoginId());

        //then
        assertThat(orderUserResponse.getOrder()
                .get(0).getOrderTotalPrice())
                .isEqualTo(10000);
        assertThat(orderUserResponse.getOrder()
                .get(0).getDeliverFee())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("주문 내역 상세 조회")
    void orderDetailFind() {

        //given
        long orderId = 1;

        //when
        OrderDetailResponse orderDetailResponse = orderService.orderDetailFind(member1.getLoginId(), orderId);

        //then
        assertThat(orderDetailResponse.getAddressDetail()).isEqualTo("상세주소");
        assertThat(orderDetailResponse.getOrderItem()
                        .get(0)
                        .getItemThumbnail().getUrl())
                .isEqualTo("itemImg1");
        assertThat(orderDetailResponse.getPay().getPayPrice()).isEqualTo(26000);
    }

    @Test
    @DisplayName("주문 등록")
    void create(){

        //given
        OrderRequest orderRequest = OrderBuilder.createOrderRequest(item1);

        //when
        long order = orderService.create(member1.getLoginId(), orderRequest);

        //then
        assertThat(order).isEqualTo(3);

        Order findOrder = orderRepository.findById(order)
                .orElseThrow(()-> new RuntimeException("NOT_FOUND_ORDER_TEST"));

        List<OrderItem> orderItem = orderItemRepository.findByOrder(findOrder);
        Pay pay = payRepository.findByOrder(findOrder);
        List<Point> point = pointRepository.findAllByMember(member1);

        assertThat(findOrder.getAddress()).isEqualTo("주소_REQUEST");
        assertThat(orderItem.size()).isEqualTo(2);
        assertThat(orderItem.get(0).getItemPrice()).isEqualTo(120000);

        assertThat(pay.getCardNum()).isEqualTo("00000001");
        assertThat(point.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("전체 취소 등록")
    void orderCancelCreate(){
        //given
        long orderId = 2;
        OrderCancelRequest orderCancelRequest = OrderBuilder.createOrderCancelRequest2();

        //when
        long orderCancel = orderService.orderCancelCreate(member2.getLoginId(), orderId, orderCancelRequest);

        //then
        Order order = orderRepository.findById(orderCancel)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_TEST"));

        PayCancel payCancel = payCancelRepository.findByOrder(order);
        assertThat(payCancel.getPayCompany()).isEqualTo("국민");


    }

    @Test
    @DisplayName("부분 취소 등록")
    void orderPartCancelCreate(){

        //given
        long orderId = 1;
        OrderCancelRequest orderCancelRequest =OrderBuilder.createOrderCancelRequest();

        //when
        long orderCancel =  orderService.orderCancelCreate(member1.getLoginId(), orderId, orderCancelRequest);

        //then
        Order order = orderRepository.findById(orderCancel)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_TEST"));

        Item item = itemRepository.findById(item1.getItemId())
                        .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM_TEST"));

        OrderItem orderItem = orderItemRepository.findByItemAndOrder(item, order)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_ITEM_TEST"));

        assertThat(orderCancel).isEqualTo(orderId);

        assertThat(order.getOrderType()).isEqualTo(OrderType.부분취소);
        assertThat(orderItem.getItemPrice()).isEqualTo(4000);
        assertThat(orderItem.getOrderItemType()).isEqualTo(OrderItemType.취소);

        PayCancel payCancel = payCancelRepository.findByOrder(order);
        assertThat(payCancel.getPayCompany()).isEqualTo("농협");

        List<Point> point = pointRepository.findAllByMember(member1);
        assertThat(point.get(1).getPointType()).isEqualTo(PointType.적립);

    }

}
