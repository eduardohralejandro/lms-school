package com.lms_schoolapp.greenteam.service;

import com.lms_schoolapp.greenteam.model.Product;
import com.lms_schoolapp.greenteam.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public void saveNewProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProductById(Long id, Product updatedProductDetails) {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        foundProduct.setName(updatedProductDetails.getName());
        foundProduct.setDescription(updatedProductDetails.getDescription());
        foundProduct.setPrice(updatedProductDetails.getPrice());
        productRepository.save(foundProduct);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProductById(Long id) {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(foundProduct);
    }
}
