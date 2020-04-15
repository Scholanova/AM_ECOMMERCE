package com.scholanova.ecommerce.order.entity;

import com.scholanova.ecommerce.cart.exception.CartException;
import com.scholanova.ecommerce.cart.exception.IllegalArgException;
import com.scholanova.ecommerce.cart.exception.NotAllowedException;
import com.scholanova.ecommerce.product.entity.Product;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class OrdersTest {

    @Test
    public void checkout_ShouldSetTheDateAndTimeOfTodayInTheOrder() throws NotAllowedException, IllegalArgException {
        //given
        Orders o = new Orders();
        o.createOrder();
        o.getCart().addProduct(new Product().create("toto","test",15.0f,1.0f,"EUR"),1);
        //when
        o.checkout();
        //then
        assertThat(o.getIssueDate().equals(Calendar.getInstance().getTime().getTime()));
    }

    @Test
    public void checkout_ShouldSetOrderStatusToPending() throws NotAllowedException, IllegalArgException {
        //given
        Orders o = new Orders();
        o.createOrder();
        o.getCart().addProduct(new Product().create("toto","test",15.0f,1.0f,"EUR"),1);
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
        o.getCart().addProduct(new Product().create("toto","test",15.0f,1.0f,"EUR"),1);
        //when
        o.setStatus(OrderStatus.CLOSED);
        //then
        assertThrows(NotAllowedException.class,() -> o.checkout());
    }

    @Test
    public void checkout_ShouldThrowIllegalArgExceptionIfCartTotalItemsQuantityIsZERO(){
        //given
        Orders o = new Orders();
        o.createOrder();
        //when
        //then
        assertThrows(IllegalArgException.class,() -> o.checkout());
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