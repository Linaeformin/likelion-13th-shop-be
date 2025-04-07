package com.likelion13th.shop.repository;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.dto.ReviewFormDto;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.entity.Review;
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
public class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("리뷰 등록 테스트")
    void saveReviewTest() {
        // 1. 멤버 저장
        Member member = new Member();
        member.setName("김민서");
        member.setEmail("MINSEO@test.com");
        member.setPassword("1234");
        member.setAddress("경기도 남양주시");
        memberRepository.save(member);

        // 2. 아이템 저장
        Item item = Item.createItem("테스트 상품", 15000, 100, "테스트용 설명", ItemSellStatus.SELL);
        itemRepository.save(item);

        // 3. DTO 생성
        ReviewFormDto dto = new ReviewFormDto();
        dto.setMemberId(member.getId()); // ✅ 이게 필요
        dto.setItemId(item.getId());
        dto.setRate(5);
        dto.setTitle("만족해요");
        dto.setContent("아주 좋습니다~");

        // 4. Review 생성
        Member foundMember = memberRepository.findById(dto.getMemberId()).orElseThrow();
        Item foundItem = itemRepository.findById(dto.getItemId()).orElseThrow();
        Review review = Review.createReview(dto, foundMember, foundItem);
        reviewRepository.save(review);

        // 5. 확인
        System.out.println(review);
    }



}
