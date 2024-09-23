package com.lms_schoolapp.greenteam.webshop.repository;

import com.lms_schoolapp.greenteam.webshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findShoppingCartByUserId(Long userId);
}
