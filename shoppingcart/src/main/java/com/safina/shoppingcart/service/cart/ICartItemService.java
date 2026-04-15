package com.safina.shoppingcart.service.cart;

import com.safina.shoppingcart.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId,Long productId,int quantity);
    void removeItemFromCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,int quantity);


    CartItem getCartItem(Long cartId, Long productId);
}
