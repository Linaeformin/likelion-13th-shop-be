package com.likelion13th.shop.service;

import com.likelion13th.shop.constant.OrderStatus;
import com.likelion13th.shop.dto.OrderDto;
import com.likelion13th.shop.dto.OrderItemDto;
import com.likelion13th.shop.dto.OrderReqDto;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.entity.OrderItem;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.repository.MemberRepository;
import com.likelion13th.shop.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public Long createNewOrder(OrderReqDto orderReqDto, String email) {

        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            member = new Member();
            member.setEmail(email);
            memberRepository.save(member);
        }

        Long itemId = orderReqDto.getItemId();

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        OrderItem orderItem = OrderItem.createOrderItem(item, orderReqDto.getCount());

        Order order = Order.createOrder(member, Collections.singletonList(orderItem));

        orderRepository.save(order);

        return order.getId();

    }

    // 모든 주문 내역 조회
    public List<OrderDto> getAllOrderByUserEmail(String email){
        List<Order> orders = orderRepository.findByMemberEmail(email);

        List<OrderDto> orderDtos = new ArrayList<>();

        // Orders 리스트에 있는 각 주문을 반복하면서 OrderDto로 변환하고 이를 OrderDtos 리스트에 추가
        orders.forEach(order -> orderDtos.add(new OrderDto().of(order)));

        return orderDtos;
    }

    // 주문 상세 조회
    public OrderItemDto getOrderDetails(Long orderId, String email) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 ID 없음 : " + orderId));

        // 주문을 생성한 사용자인지 확인
        if (order.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("주문자만 접근 가능함.");
        }

        // 주문에 속한 주문 상품들을 가져옴
        List<OrderItem> orderItems = order.getOrderItemList();

        if(!orderItems.isEmpty()){
            OrderItem orderItem = orderItems.get(0);

            return OrderItemDto.of(orderItem);
        } else {
            throw new IllegalArgumentException("주문 아이템이 없음");
        }
    }

    // 주문 취소
    public void cancelOrder(Long orderId, String email) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 ID 없음 : " + orderId));

        // 주문을 생성한 사용자인지 확인
        if (!order.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("취소 권한이 없음");
        }

        order.setOrderStatus(OrderStatus.CANCEL);   // 주문 상태를 "CANCEL"로 변경
        orderRepository.save(order);
    }

    // 주문 생성 메서드
    public Long orders(List<OrderReqDto> orderDtoList, String email) {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email);

        // 주문 아이템 리스트 생성
        List<OrderItem> orderItemList = new ArrayList<>();

        // orderDtoList 순회하며 주문 아이템 생성
        for (OrderReqDto orderReqDto : orderDtoList) {
            Item item = itemRepository.findById(orderReqDto.getItemId()).orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderReqDto.getCount());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}
