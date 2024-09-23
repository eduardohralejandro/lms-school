package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.CartItem;
import com.lms_schoolapp.greenteam.model.ShoppingCart;
import com.lms_schoolapp.greenteam.repository.CartItemRepository;
import com.lms_schoolapp.greenteam.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCart findShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findShoppingCartByUserId(userId);
    }
    @Override
    public void clearShoppingCart(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userId);

        List<CartItem> cartItems = cartItemRepository.findByShoppingCart(shoppingCart);

        if (!cartItems.isEmpty()) {
            cartItemRepository.deleteAll(cartItems);
        }

        shoppingCart.setCartItems(new ArrayList<>());
        shoppingCartRepository.save(shoppingCart);
    }
}
