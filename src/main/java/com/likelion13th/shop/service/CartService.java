package com.likelion13th.shop.service;

import com.likelion13th.shop.dto.CartDetailDto;
import com.likelion13th.shop.dto.CartItemDto;
import com.likelion13th.shop.dto.CartOrderDto;
import com.likelion13th.shop.dto.OrderReqDto;
import com.likelion13th.shop.entity.Cart;
import com.likelion13th.shop.entity.CartItem;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.repository.CartItemRepository;
import com.likelion13th.shop.repository.CartRepository;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderService orderService;

    public Long addCartItem(String email, List<CartItemDto> cartItemDtos) {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            Member newMember = new Member();
            newMember.setEmail(email);
            member = memberRepository.save(newMember);
        }

        // 회원의 장바구니를 조회
        Cart cart = cartRepository.findByMember(member);
        if (cart == null) {
            Cart newCart = Cart.createCart(member);
            cart = cartRepository.save(newCart);
        }

        // 장바구니에 상품 담기
        Long savedItemId = null;
        for (CartItemDto cartItemDto : cartItemDtos) {
            Item item = itemRepository.findById(cartItemDto.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("아이템 ID가 없음"));

            // 장바구니에 이미 있는 상품인지 확인
            CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
            if (savedCartItem != null){
                savedCartItem.addCount(cartItemDto.getCount());
                savedItemId = savedCartItem.getId();
            } else {
                CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
                CartItem savedItem = cartItemRepository.save(cartItem);
                savedItemId = savedItem.getId();
            }
        }
        return savedItemId;
    }

    public List<CartDetailDto> getCartList(String email) {
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMember(member);
        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartRepository.getCartDetail(cart.getId());
        return cartDetailDtoList;
    }

    public void deleteCartItem(String email, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        // 장바구니에 등록된 멤버 이메일과 인자로 받은 이메일값 비교
        if (!Objects.equals(cartItem.getCart().getMember().getEmail(), email)){
            throw new IllegalArgumentException();
        }

        cartItemRepository.deleteById(cartItemId);
    }

    public void updateCartItem(String email, CartDetailDto cartDetailDto) {
        CartItem cartItem = cartItemRepository.findById(cartDetailDto.getItemId()).orElseThrow(EntityNotFoundException::new);

        if(!cartItem.getCart().getMember().getEmail().equals(email)){
            throw new IllegalArgumentException();
        }

        cartItem.setCount(cartDetailDto.getItemCount());
        cartItemRepository.save(cartItem);
    }

    public Long orderCartItem(String email, List<CartOrderDto> cartOrderDtoList) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) throw new EntityNotFoundException("사용자 없음");

        List<OrderReqDto> orderReqDtoList = new ArrayList<>();

        for (CartOrderDto dto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(dto.getCartItemId())
                    .orElseThrow(() -> new EntityNotFoundException("장바구니 항목 없음: " + dto.getCartItemId()));

            OrderReqDto orderReqDto = new OrderReqDto();
            orderReqDto.setItemId(cartItem.getItem().getId());
            orderReqDto.setCount(cartItem.getCount());
            orderReqDtoList.add(orderReqDto);
        }

        Long orderId = orderService.orders(orderReqDtoList, email);

        // 주문 후 장바구니 항목 삭제
        for (CartOrderDto dto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(dto.getCartItemId())
                    .orElseThrow(() -> new EntityNotFoundException("삭제할 장바구니 항목 없음: " + dto.getCartItemId()));
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }

}
