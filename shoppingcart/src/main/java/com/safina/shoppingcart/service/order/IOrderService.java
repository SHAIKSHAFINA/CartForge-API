package com.safina.shoppingcart.service.order;

import com.safina.shoppingcart.dto.OrderDto;
import com.safina.shoppingcart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
