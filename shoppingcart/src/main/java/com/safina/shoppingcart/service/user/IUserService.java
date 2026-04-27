package com.safina.shoppingcart.service.user;

import com.safina.shoppingcart.dto.UserDto;
import com.safina.shoppingcart.model.User;
import com.safina.shoppingcart.request.CreateUserRequest;
import com.safina.shoppingcart.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest user);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
