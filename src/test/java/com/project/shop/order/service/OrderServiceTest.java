package com.project.shop.order.service;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.Builder.CategoryBuilder;
import com.project.shop.item.Builder.ItemBuilder;
import com.project.shop.item.domain.*;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.service.AuthService;
import com.project.shop.mock.WithCustomMockUser;
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
import com.project.shop.member.Builder.PointBuilder;
import com.project.shop.member.Builder.MemberBuilder;
import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.PointRepository;
import com.project.shop.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    AuthService authService;
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
    Member member1;
    Member member2;
     Item item1;
     Item item2;
     ItemImg itemImg1;
     ItemImg itemImg2;
     ItemImg itemImg3;

     Option option1;
     Option option2;
     Option option3;
     Point point1; Point point2;
    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    public void before(){
        //user
        member1 = MemberBuilder.createUser1();
        member2 = MemberBuilder.createUser2();
        memberRepository.save(member1);
        memberRepository.save(member2);

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


    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("주문 내역 전체 조회")
    void orderFindAll(){

        //given
        createOrder();

        //when
        List<OrderResponse> orderResponses= orderService.orderFindAll("loginId");

        //then
        Assertions.assertThat(orderResponses.size()).isEqualTo(2);
        Assertions.assertThat(orderResponses.get(0)
                .getOrderItem().get(1).getItemSize())
                .isEqualTo("240");

    }

    @Test
    @DisplayName("주문 내역 회원별 조회")
    void orderFindByUser(){

        //given
        createOrder();

        //when
        OrderUserResponse orderUserResponse = orderService.orderFindByUser(any());

        //then
        Assertions.assertThat(orderUserResponse.getOrder()
                .get(0).getOrderTotalPrice())
                .isEqualTo(15000);
        Assertions.assertThat(orderUserResponse.getOrder()
                .get(1).getOrderId())
                .isEqualTo(3);
    }

    @Test
    @DisplayName("주문 내역 상세 조회")
    void orderDetailFind() {

        //given
        var orderId = createOrder();

        //when
        OrderDetailResponse orderDetailResponse = orderService.orderDetailFind(any(),orderId);

        //then
        Assertions.assertThat(orderDetailResponse.getAddressDetail()).isEqualTo("상세주소");
        Assertions.assertThat(orderDetailResponse.getOrderItem()
                        .get(0)
                        .getItemThumbnail().getUrl())
                .isEqualTo("itemImg1");
        Assertions.assertThat(orderDetailResponse.getPay().getPayPrice()).isEqualTo(15000);
    }

    @Test
    @DisplayName("주문 등록")
    void create(){

        //given
        List<OrderRequest.OrderItemRequest> orderItemList = List.of(
                new OrderRequest.OrderItemRequest(item1.getItemId(), 2,10000,"220","검정"),
                new OrderRequest.OrderItemRequest(item2.getItemId(), 5,15000,"240","빨강"));

        OrderRequest orderRequest = new OrderRequest(15000,2500,"스프링","11","주소","상세주소","받는사람전화번호","배송메시지",1000,"카드사","01010",15000, orderItemList);

        //when
        var order = orderService.create(any(), orderRequest);

        //then
        Assertions.assertThat(order).isEqualTo(1);

        Order findOrder = orderRepository.findById(order)
                .orElseThrow(()-> new RuntimeException("NOT_FOUND_ORDER"));

        Assertions.assertThat(findOrder.getAddress()).isEqualTo("주소");

        List<OrderItem> orderItem = orderItemRepository.findByOrder(findOrder);
        Assertions.assertThat(orderItem.size()).isEqualTo(2);
        Assertions.assertThat(orderItem.get(0).getItemPrice()).isEqualTo(10000);

        Pay pay = payRepository.findByOrder(findOrder);
        Assertions.assertThat(pay.getCardNum()).isEqualTo("01010");

        List<Point> point = pointRepository.findAllByMember(member1);
        Assertions.assertThat(point.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("전체 취소 등록")
    void orderCancelCreate(){
        var orderId = createOrder();
        ArrayList<Long> itemList = new ArrayList<>();
        itemList.add(1L);
        itemList.add(2L);

        OrderCancelRequest orderCancelRequest = new OrderCancelRequest(itemList,"국민","01010",15000,"주문 전체 취소입니다");

        var orderCancel = orderService.orderCancelCreate(any(), orderId, orderCancelRequest);

        //then
        Order order = orderRepository.findById(orderCancel)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_TEST"));

        Assertions.assertThat(order.getOrderType()).isEqualTo(OrderType.취소);

        PayCancel payCancel = payCancelRepository.findByOrder(order);
        Assertions.assertThat(payCancel.getPayCompany()).isEqualTo("국민");

        List<Point> point = pointRepository.findAllByMember(member1);
        Assertions.assertThat(point.size()).isEqualTo(4);
        Assertions.assertThat(point.get(3).getPointType()).isEqualTo(PointType.사용취소);

    }

    @Test
    @DisplayName("부분 취소 등록")
    void orderPartCancelCreate(){
        var orderId = createOrder();
        ArrayList<Long> itemList = new ArrayList<>();
        itemList.add(1L);

        OrderCancelRequest orderCancelRequest = new OrderCancelRequest(itemList,"국민","01010",15000,"주문 전체 취소입니다");

        var orderCancel =  orderService.orderCancelCreate(any(), orderId, orderCancelRequest);

        //then
        Order order = orderRepository.findById(orderCancel)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_TEST"));

        Item item = itemRepository.findById(item1.getItemId())
                        .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM_TEST"));

        OrderItem orderItem = orderItemRepository.findByItemAndOrder(item, order)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_ITEM_TEST"));

        Assertions.assertThat(orderCancel).isEqualTo(orderId);

        Assertions.assertThat(order.getOrderType()).isEqualTo(OrderType.부분취소);
        Assertions.assertThat(orderItem.getItemPrice()).isEqualTo(10000);
        Assertions.assertThat(orderItem.getOrderItemType()).isEqualTo(OrderItemType.취소);

        PayCancel payCancel = payCancelRepository.findByOrder(order);
        Assertions.assertThat(payCancel.getPayCompany()).isEqualTo("국민");

        List<Point> point = pointRepository.findAllByMember(member1);
        Assertions.assertThat(point.get(3).getPointType()).isEqualTo(PointType.사용취소);
        Assertions.assertThat(point.get(1).getPointType()).isEqualTo(PointType.적립);

    }

    //주문
    private long createOrder() {

        List<OrderRequest.OrderItemRequest> orderItemList = List.of(
                new OrderRequest.OrderItemRequest(item1.getItemId(), 2,10000,"220","검정"),
                new OrderRequest.OrderItemRequest(item2.getItemId(), 5,15000,"240","빨강"));

        OrderRequest orderRequest = new OrderRequest(15000,2500,"스프링","11","주소","상세주소","받는사람전화번호","배송메시지",1000,"카드사","01010",15000, orderItemList);

        List<OrderRequest.OrderItemRequest> orderItemList1 = List.of(
                new OrderRequest.OrderItemRequest(item1.getItemId(), 10,10000,"220","검정"));

        OrderRequest orderRequest1 = new OrderRequest(33000,5000,"스프링1","22","주소1","상세주소1","받는사람전화번호1","배송메시지1",5000,"카드사1","01010",30000, orderItemList1);

        //when
        return orderService.create(any(), orderRequest);

    }



}
