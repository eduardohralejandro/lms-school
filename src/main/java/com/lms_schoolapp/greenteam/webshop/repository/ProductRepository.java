package com.lms_schoolapp.greenteam.webshop.repository;

import com.lms_schoolapp.greenteam.webshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
