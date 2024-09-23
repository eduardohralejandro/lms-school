package com.lms_schoolapp.greenteam.webshop.service;

import com.lms_schoolapp.greenteam.model.DeliveryOption;
import com.lms_schoolapp.greenteam.webshop.model.Order;

import java.util.List;

public interface OrderService {
    void placeOrderByUserId(Long userId, DeliveryOption option);
    List<Order> getOrdersWithProductDetails(Long userId);
}
