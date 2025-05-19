package com.likelion13th.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
// 장바구니 상품 정보
public class CartDetailDto {
    //장바구니 상품 아이디
    private Long itemId;
    //상품명
    private String itemName;
    //상품 금액
    private Integer itemPrice;
    // 상품 수량
    private Integer itemCount;
    //상품 이미지 경로
    private String imgUrl;
    // 객체 생성자 함수
    public CartDetailDto(Long itemId, String itemName, Integer itemPrice, Integer itemCount, String imgUrl) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.imgUrl = imgUrl;
    }

}
