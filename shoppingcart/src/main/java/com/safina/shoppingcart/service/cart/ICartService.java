package com.safina.shoppingcart.service.cart;

import com.safina.shoppingcart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long intializeNewCart();
}
