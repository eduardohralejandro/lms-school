package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.CartItem;
import com.lms_schoolapp.greenteam.model.Product;
import com.lms_schoolapp.greenteam.model.ShoppingCart;
import com.lms_schoolapp.greenteam.repository.CartItemRepository;
import com.lms_schoolapp.greenteam.repository.ProductRepository;
import com.lms_schoolapp.greenteam.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public void assignProductToCartItem(Long productId, Long shoppingCartId, int productQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found with id: " + shoppingCartId));

        Optional<CartItem> existingCartItem = cartItemRepository.findByProductAndShoppingCart(product, shoppingCart);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + productQuantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setShoppingCart(shoppingCart);
            newCartItem.setQuantity(productQuantity);
            cartItemRepository.save(newCartItem);
        }
    }

    @Override
    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public Optional<CartItem> removeProductFromBasket(Product selectedProduct, ShoppingCart shoppingCartOfLoggedInUser) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findByProductAndShoppingCart(selectedProduct, shoppingCartOfLoggedInUser);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItemRepository.delete(cartItem);
            return Optional.of(cartItem);
        }

        return Optional.empty();
    }
}
