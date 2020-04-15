package com.scholanova.ecommerce.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scholanova.ecommerce.cart.entity.Cart;
import com.scholanova.ecommerce.cart.exception.IllegalArgException;
import com.scholanova.ecommerce.cart.exception.NotAllowedException;
import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

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

    public void createOrder(){
        //TODO
        this.setStatus(OrderStatus.CREATED);
        this.cart = new Cart();
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

    public void getDiscount(){
        //TODO
    }

    public void getOrderPrice(){
        //TODO
    }

    public void close(){
        //TODO
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

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
