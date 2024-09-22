package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.Product;

import java.util.List;

public interface ProductService {
    void saveNewProduct(Product product);
    void updateProductById(Long id, Product updatedProductDetails);
    List<Product> findAllProducts();
    void deleteProductById(Long id);
}
