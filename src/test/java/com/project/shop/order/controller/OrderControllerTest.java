package com.project.shop.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.Builder.CategoryBuilder;
import com.project.shop.item.Builder.ItemBuilder;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.Builder.MemberBuilder;
import com.project.shop.member.Builder.PointBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import com.project.shop.member.domain.Point;
import com.project.shop.member.repository.AuthorityRepository;
import com.project.shop.member.repository.MemberRepository;
import com.project.shop.member.repository.PointRepository;
import com.project.shop.member.service.AuthService;
import com.project.shop.mock.WithCustomMockUser;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.order.repository.OrderItemRepository;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.order.repository.PayCancelRepository;
import com.project.shop.order.repository.PayRepository;
import com.project.shop.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;
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
    @Autowired
    AuthorityRepository authorityRepository;
    @BeforeEach
    public void before(){
        //user
        member1 = MemberBuilder.createUser1();
        var mem = memberRepository.save(member1);
        Authority auth = Authority.builder()
                .authName("user").member(mem).build();
        authorityRepository.save(auth);
//        member2 = MemberBuilder.createUser2();

//        memberRepository.save(member2);

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
    @WithCustomMockUser()
    @DisplayName("상품 전체 조회 테스트")
    void itemFindAll() throws Exception {
        //given
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(status().isOk()); //200 상태


    }

    @Test
    @WithCustomMockUser()
    @DisplayName("주문 등록")
    void create() throws Exception {
        //given
        List<OrderRequest.OrderItemRequest> orderItemList = List.of(
                new OrderRequest.OrderItemRequest(item1.getItemId(), 2,10000,"220","검정"),
                new OrderRequest.OrderItemRequest(item2.getItemId(), 5,15000,"240","빨강"));

        OrderRequest orderRequest = new OrderRequest(15000,2500,"스프링","11","주소","상세주소","받는사람전화번호","배송메시지",1000,"카드사","01010",15000, orderItemList);

        //when
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated());

        //then

    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("주문 수정")
    void orderUpdate() throws Exception {
        //given
        var orderId = createOrder();
        var orderItemList = orderItem(orderId);
        OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest(100000,2500,"받는분","01010","주소는","상세주소는",
                "01000000000","오기전에 문자주세요",500,"국민","20202020",50000,orderItemList);

        //when
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderUpdateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.receiverName", is("받는분")))
                .andExpect(jsonPath("$.usedPoint", is(500)))
                .andDo(print());

        //then
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
        orderService.create("loginId", orderRequest);
        return orderService.create("loginId", orderRequest1);
    }

    //주문 아이템
    private List<OrderUpdateRequest.OrderItemRequest> orderItem(long orderId){
        OrderUpdateRequest.OrderItemRequest orderItemRequest
                = new OrderUpdateRequest.OrderItemRequest(orderId, item1.getItemId(),2,30000,"240","black");

        return List.of(orderItemRequest);

    }

}
