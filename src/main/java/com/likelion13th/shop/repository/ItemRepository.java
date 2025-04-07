package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
