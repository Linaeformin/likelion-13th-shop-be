package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.constant.OrderStatus;
import com.likelion13th.shop.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
public class Item {
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private int price;
    private int stock;
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static Item createItem(String itemName, int price, int stock, String itemDetail, ItemSellStatus itemSellStatus) {
        Item item = new Item();
        item.itemName = itemName;
        item.price = price;
        item.stock = stock;
        item.itemDetail = itemDetail;
        item.itemSellStatus = itemSellStatus;
        item.createdBy = LocalDateTime.now(); // 선택
        item.modifiedBy = LocalDateTime.now(); // 선택
        return item;
    }

}
