package com.scholanova.ecommerce.order.entity;

import com.scholanova.ecommerce.cart.exception.CartException;
import com.scholanova.ecommerce.cart.exception.NotAllowedException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class OrdersTest {

    @Test
    public void checkout_ShouldSetTheDateAndTimeOfTodayInTheOrder() throws NotAllowedException {
        //given
        Orders o = new Orders();
        o.createOrder();
        //when
        o.checkout();
        //then
        assertThat(o.getIssueDate().equals(Calendar.getInstance().getTime().getTime()));
    }

    @Test
    public void checkout_ShouldSetOrderStatusToPending() throws NotAllowedException {
        //given
        Orders o = new Orders();
        o.createOrder();
        //when
        o.checkout();
        //then
        assertThat(o.getStatus().equals(OrderStatus.PENDING));
    }

    @Test
    public void checkout_ShouldThrowNotAllowedExceptionIfStatusIsClosed() throws NotAllowedException {
        //given
        Orders o = new Orders();
        o.createOrder();
        //when
        o.setStatus(OrderStatus.CLOSED);
        //then
        assertThrows(NotAllowedException.class,() -> o.checkout());
    }

    @Test
    public void checkout_ShouldThrowIllegalArgExceptionIfCartTotalItemsQuantityIsZERO(){

    }

    @Test
    public void setCart_ShouldThrowNotAllowedExceptionIfStatusIsClosed(){

    }

    @Test
    public void createOrder_ShouldSetTheCartInTheOrder(){

    }

    @Test
    public void createOrder_ShouldSetStatusToCreated(){

    }

    @Test
    public void getDiscount_shouldReturnZEROIFCartTotalPriceIsLessThan100(){

    }

    @Test
    public void getDiscount_shouldReturn5percentIfCartTotalPriceIsMoreOrEqual100(){

    }

    @Test
    public void getOrderPrice_shouldReturnTotalPriceWithDiscount(){

    }

    @Test
    public void close_ShouldSetStatusToClose(){

    }

}