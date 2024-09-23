package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.repository.CartItemRepository;
import com.lms_schoolapp.greenteam.repository.OrderRepository;
import com.lms_schoolapp.greenteam.repository.ShoppingCartRepository;
import com.lms_schoolapp.greenteam.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository<User> userRepository;
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public void placeOrderByUserId(Long userId, DeliveryOption option) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByShoppingCart(shoppingCart);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Shopping cart is empty");
        }

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setDeliveryOption(option);
        newOrder.setOrderDate(LocalDateTime.now());

        for (CartItem cartItem : cartItems) {
            cartItem.setOrder(newOrder);
        }

        newOrder.setCartItems(cartItems);

        orderRepository.save(newOrder);

        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
    }


    @Override
    @Transactional
    public List<Order> getOrdersWithProductDetails(Long userId) {
        return orderRepository.findByUser(userId);
    }
}
