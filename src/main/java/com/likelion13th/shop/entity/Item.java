package com.likelion13th.shop.entity;

import com.likelion13th.shop.Exception.OutOfStockException;
import com.likelion13th.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter @Setter
@ToString
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

    private String itemImg;
    private String itemImgPath;

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

    public void removeStock(int stock){
        int restStock = this.stock - stock;
        if (restStock < 0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량:" + this.stock + ")");
        }
        this.stock = stock;
    }

    public void addStock(int stock){
        this.stock += stock;
    }

}
