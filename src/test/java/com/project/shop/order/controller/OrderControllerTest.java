package com.project.shop.order.controller;

import com.project.shop.common.controller.ControllerCommon;
import com.project.shop.item.builder.CategoryBuilder;
import com.project.shop.item.builder.ItemBuilder;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.builder.PointBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import com.project.shop.member.domain.Point;
import com.project.shop.member.repository.PointRepository;
import com.project.shop.mock.WithCustomMockUser;
import com.project.shop.order.builder.OrderBuilder;
import com.project.shop.order.domain.*;
import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.order.repository.OrderItemRepository;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.order.repository.PayCancelRepository;
import com.project.shop.order.repository.PayRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest extends ControllerCommon {

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
        var memberSave = memberRepository.save(member1);
        member2 = MemberBuilder.createUser2();
        member2 = memberBuilder.signUpAdminMember();
        var memberAdminSave = memberRepository.save(member2);

        Authority auth = memberBuilder.auth(memberSave);
        Authority authAdmin = memberBuilder.authAdmin(memberAdminSave);
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
        option3 = ItemBuilder.createOption3(item1);
        optionRepository.save(option1);
        optionRepository.save(option2);
        optionRepository.save(option3);

        //point
        point1 = PointBuilder.createPoint1(member1);
        point2 = PointBuilder.createPoint2(member1);
        pointRepository.save(point1);
        pointRepository.save(point2);

        order = OrderBuilder.createOrder(member1);
        order2 = OrderBuilder.createOrder(member2);
        OrderItem orderItem1 = OrderBuilder.createOrderItem1(item1, order);
        OrderItem orderItem2 = OrderBuilder.createOrderItem2(item1, order2);
        Pay pay = OrderBuilder.createPay(order);

        orderRepository.save(order);
        orderRepository.save(order2);
        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);
        payRepository.save(pay);

    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("주문내역 조회")
    void orderFindAll() throws Exception {

        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].orderState").value("완료"))
                .andExpect(jsonPath("$[0].orderItem[1].itemSize").value("240"));

    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("주문내역 회원별 조회")
    void orderFindByUser() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/orders"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.order.*",hasSize(1)));

    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("주문내역 상세 조회")
    void detailFind() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/{orderId}",1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.orderItem[0].itemName").value("조던"))
                .andExpect(jsonPath("$.receiverPhoneNum").value("01011111111"));

    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("주문 등록")
    void orderCreate() throws Exception {
        //given
        OrderRequest orderRequest = OrderBuilder.createOrderRequest(item1);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .content(objectMapper.writeValueAsString(orderRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //then
        Assertions.assertThat(orderRepository.findById(1L).get().getDeliverFee()).isEqualTo(0);
        Assertions.assertThat(payRepository.findById(1L).get().getCardNum()).isEqualTo("00000001");
        Assertions.assertThat(orderItemRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("주문 수정")
    void orderUpdate() throws Exception {
        //given
        OrderUpdateRequest orderUpdateRequest = OrderBuilder.createOrderUpdateRequest(order, item1);

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/{orderId}",1)
                        .content(objectMapper.writeValueAsString(orderUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        Assertions.assertThat(orderRepository.findById(1L).get().getDeliverFee()).isEqualTo(2500);
        Assertions.assertThat(payRepository.findById(1L).get().getCardNum()).isEqualTo("10000001");
        Assertions.assertThat(orderItemRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(pointRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("부분취소, 취소 등록")
    void orderCancelCreate() throws Exception {
        //given
        OrderCancelRequest orderCancelRequest = OrderBuilder.createOrderCancelRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/{orderId}/cancels",1)
                        .content(objectMapper.writeValueAsString(orderCancelRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //then
        Assertions.assertThat(payCancelRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(orderItemRepository.findById(1L).get().getOrderItemType()).isEqualTo(OrderItemType.취소);

    }

}
