package com.likelion13th.shop.repository;

import com.likelion13th.shop.constant.Role;
import com.likelion13th.shop.entity.Member;
import jakarta.transaction.Transactional;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
@Slf4j
@ToString
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void saveMemberTest() {
        // Member 객체 생성
        Member member = new Member();
        member.setName("김민서");
        member.setEmail("alstj@example.com");
        member.setPassword("password123");
        member.setAddress("경기도 남양주시");

        // 저장
        memberRepository.save(member);

        // 출력 (toString 자동 호출됨)
        System.out.println(member.toString());
    }
}