package com.project.shop.ordersheet.repository;
import com.project.shop.ordersheet.domain.OrderSheet;
import com.project.shop.ordersheet.repository.OrderSheetRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class OrderSheetRepositoryTest {

        @Autowired
        private OrderSheetRepository orderSheetRepository;

        private OrderSheet orderSheet;

        @BeforeEach
        public void setUp() {

//                orderSheet = OrderSheet
//                        .builder()
//                        .usedPoint(3000)
//                        .itemSumPrice(50000)
//                        .finalPrice(47000)
//                        .deliverFee(2500)
//                        .receiverName("양선")
//                        .zipcode("11111")
//                        .address("한국")
//                        .addrDetail("서울")
//                        .phoneNum("01011111111")
//                        .dateTime(LocalDateTime.now())
//                        .build();
        }

        @Order(2)
        @Test
        void saveOrderSheet(){

//                OrderSheet saveOrderSheet = orderSheetRepository.save(orderSheet);
//
//                assertNotNull(saveOrderSheet);
//                assertThat(saveOrderSheet.getReceiverName()).isEqualTo("양선");
        }


        @Order(1)
        @Test
        void findOrderSheet_size0(){

                long orderSheetId = 1L;
                Optional<OrderSheet> findOrderSheet = orderSheetRepository.findById(orderSheetId);

                assertThatThrownBy(() -> {
                        if (!findOrderSheet.isPresent()) {
                                throw new IllegalStateException("size0");
                        }
                }).isInstanceOf(IllegalStateException.class);
        }

        @Test
        void findOrderSheet(){

                //given
                orderSheetRepository.save(orderSheet);

                long orderSheetId = 1L;
                Optional<OrderSheet> findOrderSheet = orderSheetRepository.findById(orderSheetId);

                assertThat(findOrderSheet.get().getAddress()).isEqualTo("한국");
//                assertThat(findOrderSheet.get().getReceiverName()).isNotNull();
        }

}


