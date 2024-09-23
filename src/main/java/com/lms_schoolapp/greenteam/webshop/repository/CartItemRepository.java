package com.lms_schoolapp.greenteam.webshop.repository;

import com.lms_schoolapp.greenteam.webshop.model.CartItem;
import com.lms_schoolapp.greenteam.model.Product;
import com.lms_schoolapp.greenteam.webshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductAndShoppingCart(Product product, ShoppingCart shoppingCart);
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
