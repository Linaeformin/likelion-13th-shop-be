package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name="orderItem")
@Getter
public class OrderItem {
    @Id
    @Column(name="orderItem_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id", nullable = false)  // 🔥 NOT NULL 설정
    private Order order;

    @OneToOne
    @JoinColumn(name="item_id", nullable = false)  // 🔥 NOT NULL 설정
    private Item item;

    private int price;
    private int count;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}
