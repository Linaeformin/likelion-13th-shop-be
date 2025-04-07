package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name="cartItem")
@Getter
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

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}
