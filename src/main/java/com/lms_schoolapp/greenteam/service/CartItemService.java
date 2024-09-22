package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.CartItem;
import com.lms_schoolapp.greenteam.model.Product;
import com.lms_schoolapp.greenteam.model.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    void assignProductToCartItem(Long productId, Long shoppingCartId, int productQuantity);
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    Optional<CartItem> removeProductFromBasket(Product selectedProduct, ShoppingCart shoppingCartOfLoggedInUser);
}
