package com.lms_schoolapp.greenteam.webshop.service;

import com.lms_schoolapp.greenteam.webshop.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart findShoppingCartByUserId(Long userId);
    void clearShoppingCart(Long userId);
}
