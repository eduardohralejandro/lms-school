package com.lms_schoolapp.greenteam.repository;

import com.lms_schoolapp.greenteam.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
