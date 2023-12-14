package com.project.shop.order.service;

import com.project.shop.item.data.CategoryData;
import com.project.shop.item.data.ItemData;
import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.OrderItem;
import com.project.shop.order.domain.Pay;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.response.OrderDetailResponse;
import com.project.shop.order.dto.response.OrderResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.repository.OrderItemRepository;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.order.repository.PayCancelRepository;
import com.project.shop.order.repository.PayRepository;
import com.project.shop.user.Data.UserData;
import com.project.shop.user.domain.User;
import com.project.shop.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    PayRepository payRepository;

    //상품 등록
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ItemImgRepository itemImgRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
     OptionRepository optionRepository;
    User user1;
    User user2;
     Item item1;
     Item item2;
     ItemImg itemImg1;
     ItemImg itemImg2;
     ItemImg itemImg3;

     Option option1;
     Option option2;
     Option option3;
    @Autowired
    ItemRepository itemRepository;
    @BeforeEach
    public void before(){

        //user
        user1 = UserData.createUser1();
        user2 = UserData.createUser2();
        userRepository.save(user1);
        userRepository.save(user2);

        //category
        Category category = CategoryData.createCategory1();
        categoryRepository.save(category);

        //item
        item1 = ItemData.createItem1(category);
        item2 = ItemData.createItem2(category);
        itemRepository.save(item1);
        itemRepository.save(item2);

        //itemImg
        itemImg1 = ItemData.createImg1(item1);
        itemImg2 = ItemData.createImg2(item1);
        itemImg3 = ItemData.createImg3(item2);
        itemImgRepository.save(itemImg1);
        itemImgRepository.save(itemImg2);
        itemImgRepository.save(itemImg3);

        //option
        option1 = ItemData.createOption1(item1);
        option2 = ItemData.createOption2(item1);
        option3 = ItemData.createOption3(item2);
        optionRepository.save(option1);
        optionRepository.save(option2);
        optionRepository.save(option3);
    }

    @Test
    @DisplayName("주문 내역 전체 조회")
    void orderFindAll(){

        //given
        createOrder();

        //when
        List<OrderResponse> orderResponses= orderService.orderFindAll();

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
        OrderUserResponse orderUserResponse = orderService.orderFindByUser(user1.getUserId());

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
        OrderDetailResponse orderDetailResponse = orderService.orderDetailFind(orderId);

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
        var order = orderService.create(user2.getUserId(), orderRequest);

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
        var orderId = orderService.create(user1.getUserId(), orderRequest);
        orderService.create(user2.getUserId(),orderRequest1);
        orderService.create(user1.getUserId(),orderRequest1);

        return orderId;

    }

}
