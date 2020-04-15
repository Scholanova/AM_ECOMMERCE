package com.scholanova.ecommerce.order.entity;

import com.scholanova.ecommerce.cart.entity.Cart;
import com.scholanova.ecommerce.cart.exception.CartException;
import com.scholanova.ecommerce.cart.exception.IllegalArgException;
import com.scholanova.ecommerce.cart.exception.NotAllowedException;
import com.scholanova.ecommerce.product.entity.Product;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class OrdersTest {

    @Test
    public void checkout_ShouldSetTheDateAndTimeOfTodayInTheOrder() throws NotAllowedException, IllegalArgException {
        //given
        Orders o = Orders.createOrder("123",new Cart());
        o.getCart().addProduct(Product.create("toto","test",15.0f,1.0f,"EUR"),1);
        //when
        o.checkout();
        //then
        assertThat(o.getIssueDate()).isEqualTo(new Date(Calendar.getInstance().getTime().getTime()));
    }

    @Test
    public void checkout_ShouldSetOrderStatusToPending() throws NotAllowedException, IllegalArgException {
        //given

        Orders o = Orders.createOrder("123",new Cart());
        o.getCart().addProduct(Product.create("toto","test",15.0f,1.0f,"EUR"),1);
        //when
        o.checkout();
        //then
        assertThat(o.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    public void checkout_ShouldThrowNotAllowedExceptionIfStatusIsClosed() throws NotAllowedException {
        //given
        Orders o = Orders.createOrder("123",new Cart());
        o.getCart().addProduct(Product.create("toto","test",15.0f,1.0f,"EUR"),1);
        //when
        o.setStatus(OrderStatus.CLOSED);
        //then
        assertThrows(NotAllowedException.class,() -> o.checkout());
    }

    @Test
    public void checkout_ShouldThrowIllegalArgExceptionIfCartTotalItemsQuantityIsZERO(){
        //given
        Orders o = Orders.createOrder("123",new Cart());
        //when
        //then
        assertThrows(IllegalArgException.class,() -> o.checkout());
    }

    @Test
    public void setCart_ShouldThrowNotAllowedExceptionIfStatusIsClosed(){
        //given
        Orders o = Orders.createOrder("123",new Cart());
        o.getCart().addProduct(Product.create("toto","test",15.0f,1.0f,"EUR"),1);
        //when
        o.setStatus(OrderStatus.CLOSED);
        //then
        assertThrows(NotAllowedException.class,() -> o.setCart(new Cart()));
    }

    @Test
    public void createOrder_ShouldSetTheCartInTheOrder(){
        //given
        Cart c = new Cart();
        //when
        Orders o = Orders.createOrder("123",c);
        //then
        assertThat(c).isEqualTo(o.getCart());
    }

    @Test
    public void createOrder_ShouldSetStatusToCreated(){
        //given
        Cart c = new Cart();
        //then
        Orders o = Orders.createOrder("123",c);
        assertThat(o.getStatus()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    public void getDiscount_shouldReturnZEROIFCartTotalPriceIsLessThan100(){
        //given
        Cart c = new Cart();
        Orders o = Orders.createOrder("123",c);
        //when
        o.getCart().addProduct(Product.create("toto","test",15.0f,1.0f,"EUR"),1);
        //then
        assertThat(o.getDiscount()).isEqualTo(new BigDecimal(0));
    }

    @Test
    public void getDiscount_shouldReturn5percentIfCartTotalPriceIsMoreOrEqual100(){
        //given
        Cart c = new Cart();
        Orders o = Orders.createOrder("123",c);
        //when
        o.getCart().addProduct(Product.create("toto","test",100.0f,1.0f,"EUR"),1);
        //then
        assertThat(o.getDiscount()).isEqualTo(new BigDecimal(5));
    }

    @Test
    public void getOrderPrice_shouldReturnTotalPriceWithDiscount(){
        Cart c = new Cart();
        Cart c2 = new Cart();
        Orders o = Orders.createOrder("123",c);
        Orders or = Orders.createOrder("2",c2);
        //when
        o.getCart().addProduct(Product.create("toto","test",100.0f,1.0f,"EUR"),1);
        or.getCart().addProduct(Product.create("toto","test",10.0f,1.0f,"EUR"),1);
        //then
        assertThat(o.getOrderPrice()).isEqualTo(o.getCart().getTotalPrice().multiply(BigDecimal.valueOf(0.95)));
        assertThat(or.getOrderPrice()).isEqualTo(or.getCart().getTotalPrice().multiply(BigDecimal.valueOf(1)));
    }

    @Test
    public void close_ShouldSetStatusToClose(){
        //given
        Cart c = new Cart();
        Orders o = Orders.createOrder("123",c);
        //when
        o.close();
        //then
        assertThat(o.getStatus()).isEqualTo(OrderStatus.CLOSED);
    }

}