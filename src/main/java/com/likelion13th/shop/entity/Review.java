package com.likelion13th.shop.entity;

import com.likelion13th.shop.dto.ReviewFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Entity
@Table(name="review")
@Getter @Setter
public class Review {
    //PK 설정
    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name="item_id", nullable = false)
    private Item item;

    private int rate;
    private String title;
    private String content;
    private String oriImgName;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static Review createReview(ReviewFormDto reviewFormDto, Member member, Item item) {
        Review review = new Review();
        review.setMember(member);  // ID로 조회해서 넣을 예정
        review.setItem(item);
        review.setRate(reviewFormDto.getRate());
        review.setTitle(reviewFormDto.getTitle());
        review.setContent(reviewFormDto.getContent());
        review.setOriImgName(reviewFormDto.getOriImgName());

        return review;
    }

}
