package com.safina.shoppingcart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class apiResponse {
    private String message;
    private Object data;
}
