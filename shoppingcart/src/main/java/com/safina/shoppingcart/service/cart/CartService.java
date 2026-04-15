package com.safina.shoppingcart.service.cart;

import com.safina.shoppingcart.exceptions.ResourceNotFoundException;
import com.safina.shoppingcart.model.Cart;
import com.safina.shoppingcart.model.CartItem;
import com.safina.shoppingcart.repository.CartItemRepository;
import com.safina.shoppingcart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRespository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator=new AtomicLong();

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRespository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount=cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRespository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart=getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRespository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart=getCart(id);
        return cart.getItems().stream()
                .map(CartItem:: getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Long intializeNewCart(){
        Cart newCart=new Cart();
        Long newCartId=cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRespository.save(newCart).getId();
    }
}
