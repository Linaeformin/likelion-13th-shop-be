package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.OrderStatus;
import com.likelion13th.shop.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name="cart")
@Getter
public class Cart {
    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}