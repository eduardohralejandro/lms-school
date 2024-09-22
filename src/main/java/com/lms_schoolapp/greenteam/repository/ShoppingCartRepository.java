package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findShoppingCartByUserId(Long userId);
}
