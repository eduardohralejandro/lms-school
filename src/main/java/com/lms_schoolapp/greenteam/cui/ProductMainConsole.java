package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.service.ProductService;
import com.lms_schoolapp.greenteam.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMainConsole {
    private final ProductService productService;

    public void printOptions(User loggedInUser) {
        boolean continueSelection = false;
        while (!continueSelection) {
            ProductMainOption option = KeyboardUtility.askForElementInArray(ProductMainOption.values());
            switch (option) {
                case CREATE_PRODUCT -> startCreateProduct();
                case DELETE_PRODUCT -> startDeleteProduct();
                case EDIT_PRODUCT -> startEditProduct();
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    public void startCreateProduct() {
        System.out.println("Create product");
        Product product = createProduct();
        productService.saveNewProduct(product);
        System.out.printf("%s was successfully created\n", product.getName());
    }

    public void startEditProduct() {
        System.out.println("If you don't desire to update simply press enter in the value");
        List<Product> allProducts = productService.findAllProducts();
        if (allProducts.isEmpty()) {
            System.out.println("No products found");
        } else {
            Product selectedProduct = (Product) KeyboardUtility.askForElementInArray(allProducts.toArray());
            String productTitle = askForProductTitle();
            String description = askForProductDescription();
            Double price = askForProductPrice();

            if (!productTitle.trim().isEmpty()) {
                selectedProduct.setName(productTitle);
            }
            if (!description.trim().isEmpty()) {
                selectedProduct.setDescription(description);
            }
            if (price != null) {
                selectedProduct.setPrice(price);
            }

            productService.updateProductById(selectedProduct.getId(), selectedProduct);
        }

    }

    public void startDeleteProduct() {
        System.out.println("Select Product to be deleted");
        List<Product> allProducts = productService.findAllProducts();
        if (allProducts.isEmpty()) {
            System.out.println("No products found");
        } else {
            Product selectedProduct = (Product) KeyboardUtility.askForElementInArray(allProducts.toArray());
            if (selectedProduct == null) {
                System.out.println("No product selected");
            } else {
                productService.deleteProductById(selectedProduct.getId());
            }
        }
    }

    public String askForProductTitle() {
        return KeyboardUtility.askForString("Enter product title: ");
    }

    public String askForProductDescription() {
        return KeyboardUtility.askForString("Enter product description: ");
    }

    public Double askForProductPrice() {
        while (true) {
            try {
                String input = KeyboardUtility.askForString("Enter the product price (or press Enter to skip): ");

                if (input.trim().isEmpty()) {
                    return null;
                }

                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public Product createProduct() {
        String productTitle = askForProductTitle();
        String productDescription = askForProductDescription();
        Double productPrice = askForProductPrice();
        Product newProduct = new Product();
        newProduct.setName(productTitle);
        newProduct.setDescription(productDescription);
        newProduct.setPrice(productPrice);
        return newProduct;
    }

}
