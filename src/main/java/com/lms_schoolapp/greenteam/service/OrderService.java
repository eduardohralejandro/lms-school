package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.DeliveryOption;
import com.lms_schoolapp.greenteam.model.Order;

import java.util.List;

public interface OrderService {
    void placeOrderByUserId(Long userId, DeliveryOption option);
    List<Order> getOrdersWithProductDetails(Long userId);
}
