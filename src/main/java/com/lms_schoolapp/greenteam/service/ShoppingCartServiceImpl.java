package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.ShoppingCart;
import com.lms_schoolapp.greenteam.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCart findShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findShoppingCartByUserId(userId);
    }
}
