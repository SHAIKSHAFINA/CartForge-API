package com.safina.shoppingcart.service.cart;

import com.safina.shoppingcart.exceptions.ResourceNotFoundException;
import com.safina.shoppingcart.model.Cart;
import com.safina.shoppingcart.model.CartItem;
import com.safina.shoppingcart.model.User;
import com.safina.shoppingcart.repository.CartItemRepository;
import com.safina.shoppingcart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRespository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRespository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount=cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRespository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart=getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.clearCart();
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
    public Cart intializeNewCart(User user){
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(()-> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRespository.save(cart);
                });
    }

    @Override
    public Cart getCartByUserId(Long userId){
        return cartRespository.findByUserId(userId);
    }

}
