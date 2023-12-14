package com.project.shop.order.service;

import com.project.shop.item.data.CategoryData;
import com.project.shop.item.data.ItemData;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.OrderType;
import com.project.shop.order.domain.PayCancel;
import com.project.shop.order.domain.PayCancelType;
import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderPartCancelRequest;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.repository.OrderItemRepository;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.order.repository.PayCancelRepository;
import com.project.shop.order.repository.PayRepository;
import com.project.shop.user.Data.PointData;
import com.project.shop.user.Data.UserData;
import com.project.shop.user.domain.Point;
import com.project.shop.user.domain.PointType;
import com.project.shop.user.domain.User;
import com.project.shop.user.repository.PointRepository;
import com.project.shop.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderCancelServiceTest {

    @Autowired
    OrderCancelService orderCancelService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PayCancelRepository payCancelRepository;
    @Autowired
    PointRepository pointRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    ItemImgRepository itemImgRepository;
    User user1; User user2;
    Item item1; Item item2;
    ItemImg itemImg1; ItemImg itemImg2; ItemImg itemImg3;

    Option option1; Option option2; Option option3;
    Point point1; Point point2;

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

        //point
        point1 = PointData.createPoint1(user1);
        point2 = PointData.createPoint2(user1);
        pointRepository.save(point1);
        pointRepository.save(point2);

    }

    @Test
    @DisplayName("부분 취소 등록")
    void partCancelCreate(){

        //given
        ArrayList<Long> item = new ArrayList<>();
        item.add(1L);
        item.add(2L);

        //주문
        var orderId = createOrder();

        OrderPartCancelRequest orderPartCancelRequest = new OrderPartCancelRequest(item,"농협","01010",15000,"주문 취소입니다!", "부분취소",500);

        //when
        var orderCancel = orderCancelService.partCancelCreate(user1.getUserId(), orderId,orderPartCancelRequest);

        //then
        Order order = orderRepository.findById(orderCancel)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_TEST"));

        Assertions.assertThat(order.getOrderType()).isEqualTo(OrderType.부분취소);

        PayCancel payCancel = payCancelRepository.findByOrder(order);
        Assertions.assertThat(payCancel.getPayCompany()).isEqualTo("농협");

        List<Point> point = pointRepository.findAllByUsers(user1);
        Assertions.assertThat(point.get(2).getPointType()).isEqualTo(PointType.사용취소);

    }

    @Test
    @DisplayName("취소 등록")
    void orderCancelCreate(){
        var orderId = createOrder();
        OrderCancelRequest orderCancelRequest = new OrderCancelRequest("국민","01010",15000,"주문 전체 취소입니다", "취소",500);

        var orderCancel =  orderCancelService.orderCancelCreate(user1.getUserId(), orderId, orderCancelRequest);

        //then
        Order order = orderRepository.findById(orderCancel)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ORDER_TEST"));

        Assertions.assertThat(order.getOrderType()).isEqualTo(OrderType.취소);

        PayCancel payCancel = payCancelRepository.findByOrder(order);
        Assertions.assertThat(payCancel.getPayCompany()).isEqualTo("국민");

        List<Point> point = pointRepository.findAllByUsers(user1);
        Assertions.assertThat(point.get(2).getPointType()).isEqualTo(PointType.사용취소);

    }
    private long createOrder() {

        List<OrderRequest.OrderItemRequest> orderItemList = List.of(
                new OrderRequest.OrderItemRequest(item1.getItemId(), 2,10000,"220","검정"),
                new OrderRequest.OrderItemRequest(item2.getItemId(), 5,15000,"240","빨강"));

        OrderRequest orderRequest = new OrderRequest(15000,2500,"스프링","11","주소","상세주소","받는사람전화번호","배송메시지",1000,"카드사","01010",15000, orderItemList);

        List<OrderRequest.OrderItemRequest> orderItemList1 = List.of(
                new OrderRequest.OrderItemRequest(item1.getItemId(), 10,10000,"220","검정"));

        OrderRequest orderRequest1 = new OrderRequest(33000,5000,"스프링1","22","주소1","상세주소1","받는사람전화번호1","배송메시지1",5000,"카드사1","01010",30000, orderItemList1);

        //when
        var orderId = orderService.create(user1.getUserId(),orderRequest);
        orderService.create(user2.getUserId(), orderRequest1);

        return orderId;

    }
}
