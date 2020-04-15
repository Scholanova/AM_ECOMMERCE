package com.scholanova.ecommerce.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scholanova.ecommerce.cart.entity.Cart;
import com.scholanova.ecommerce.cart.exception.IllegalArgException;
import com.scholanova.ecommerce.cart.exception.NotAllowedException;
import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;
//import java.sql.Date;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity(name="orders")
public class Orders {

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column
    private String number;

    @Column
    private Date issueDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id", referencedColumnName = "id")
    private Cart cart;

    public Orders() {
    }

    public static Orders createOrder(String number, Cart cart){
        //TODO
        /*this.setStatus(OrderStatus.CREATED);
        this.cart = new Cart();*/
        Orders orders = new Orders();
        orders.number = number;
        orders.status = OrderStatus.CREATED;
        orders.cart = cart;
        return orders;
    }

    public void checkout() throws NotAllowedException, IllegalArgException {
        //TODO
        //Si le cart est sans items car un produit avec quantité nulle lève déjà une exception
        if(this.getCart().getCartItems().size()==0){
            throw new IllegalArgException("il n'y a rien dans votre panier");
        }
        if(this.getStatus().equals(OrderStatus.CLOSED)){
            throw new NotAllowedException("Vous n'êtes pas autorisé à faire ceci");
        }else{
            this.setStatus(OrderStatus.PENDING);
            this.setIssueDate(new Date(Calendar.getInstance().getTime().getTime()));
        }
    }

    public BigDecimal getDiscount(){
        //TODO
        BigDecimal total = this.getCart().getTotalPrice();
        if(total.floatValue() < 100f){
            return new BigDecimal(0);
        }else {
            return new BigDecimal(5);
        }
    }

    public BigDecimal getOrderPrice(){
        //TODO
        BigDecimal valueBeforeDiscount = this.getCart().getTotalPrice();



        BigDecimal total = valueBeforeDiscount.multiply(new BigDecimal(1).subtract(this.getDiscount().divide(new BigDecimal(100))));


        return total;
    }

    public void close(){
        //TODO
        this.status = OrderStatus.CLOSED;
    }


    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {return number;}

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getIssueDate() {return issueDate;}

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public OrderStatus getStatus() {return status;}

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Cart getCart() {return cart;}

    public void setCart(Cart cart) throws NotAllowedException {
        if(this.getStatus().equals(OrderStatus.CLOSED)){
            throw new NotAllowedException("Vous n'êtes pas autorisé à modifier le panier");
        }else{
            this.cart = cart;
        }
    }
}
