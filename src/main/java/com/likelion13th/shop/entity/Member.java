package com.likelion13th.shop.entity;

import com.likelion13th.shop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.likelion13th.shop.constant.Role;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member {
    // PK 설정
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique=true)
    private String email;
    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    // 올바르게 클래스 내부에 위치
    public static Member createMember(MemberFormDto memberFormDto){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPassword(memberFormDto.getPassword());
        member.setAddress(memberFormDto.getAddress());
        return member;
    }
}



