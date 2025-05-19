package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="cartItem")
@Getter @Setter
public class CartItem {
    @Id
    @Column(name="cartItem_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable = false)  // 🔥 NOT NULL 설정
    private Cart cart;

    @OneToOne
    @JoinColumn(name="item_id", nullable = false)  // 🔥 NOT NULL 설정
    private Item item;

    private int count;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static CartItem createCartItem (Cart cart, Item item, Integer count) {
        // CartItem 객체 생성
        CartItem cartItem = new CartItem();

        // 생성한 객체에 Cart 객체 설정(set)
        cartItem.setCart(cart);

        // 생성한 객체에 Item 객체 설정(set)
        cartItem.setItem(item);

        // 생성한 객체에 상품 수량 설정(set)
        cartItem.setCount(count);

        // 반환
        return cartItem;

    }

    public void addCount(Integer count) {
        this.count += count;
    }

    public void updateCount(Integer count) {
        this.count = count;
    }
}
