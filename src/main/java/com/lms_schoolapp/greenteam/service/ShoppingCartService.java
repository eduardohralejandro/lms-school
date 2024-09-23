package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart findShoppingCartByUserId(Long userId);
    void clearShoppingCart(Long userId);
}
