package com.likelion13th.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberFormDto {
    private String name;
    public String email;
    public String password;
    public String address;
}
