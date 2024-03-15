package com.project.shop.ordersheet.service;

import com.project.shop.item.repository.ItemRepository;
import com.project.shop.member.service.PointService;
import com.project.shop.ordersheet.domain.OrderSheet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderSheetServiceTest {

    @Mock
    private PointService pointService;
    @InjectMocks
    private OrderSheet orderSheet;

    @Test
    void calculateAmountAndPrice_NoPoints(){

        //given
        long userId = 1L;
        when(pointService.calculatePoint(userId)).thenReturn(0);

        int totalPrice = orderSheet.calculateTotalPrice();

        assertThat(totalPrice).isEqualTo();
    }

    @Test
    void calculateAmountAndPrice_WithPoints(){

        //given
        long userId = 1L;
        when(pointService.calculatePoint(userId)).thenReturn(3500);

        int totalPrice = orderSheet.calculateTotalPrice();

        assertThat(totalPrice).isEqualTo();

    }

}
