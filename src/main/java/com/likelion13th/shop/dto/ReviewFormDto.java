package com.likelion13th.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewFormDto {
    private Long memberId;
    private Long itemId;
    private Integer rate;
    private String title;
    private String content;
    private String oriImgName;
}
