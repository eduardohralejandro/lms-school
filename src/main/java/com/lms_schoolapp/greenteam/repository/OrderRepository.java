package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Order;
import com.lms_schoolapp.greenteam.model.User;
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