package com.safina.shoppingcart.controller;

import com.safina.shoppingcart.exceptions.ResourceNotFoundException;
import com.safina.shoppingcart.model.Cart;
import com.safina.shoppingcart.response.apiResponse;
import com.safina.shoppingcart.service.cart.CartService;
import com.safina.shoppingcart.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<apiResponse> getCart(@PathVariable Long cartId){
        try {
            Cart cart=cartService.getCart(cartId);
            return ResponseEntity.ok(new apiResponse("Sucess" , cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND  ).body(new apiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<apiResponse> clearCart(@PathVariable Long cartId){
        try {
            cartService.clearCart(cartId);
            return  ResponseEntity.ok(new apiResponse("Clear CartSucess" , null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND  ).body(new apiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{}/cart/total-price")
    public ResponseEntity<apiResponse> getTotalAmount(@PathVariable Long cartId){
        try {
            BigDecimal totalPrice=cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new apiResponse("Sucess" , totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND  ).body(new apiResponse(e.getMessage(), null));
        }
    }
}
