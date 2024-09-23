package com.lms_schoolapp.greenteam.webshop.repository;

import com.lms_schoolapp.greenteam.webshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.cartItems ci " +
            "LEFT JOIN FETCH ci.product " +
            "WHERE o.user.id = :userId")
    List<Order> findByUser(@Param("userId") Long userId);
}
