package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.service.CartItemService;
import com.lms_schoolapp.greenteam.service.ProductService;
import com.lms_schoolapp.greenteam.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebshopMainConsole {
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    public void selectOptionWebshop(User loggedInUser) {
        System.out.println("Select operation in web shop");
        boolean continueSelection = false;
        while (!continueSelection) {
            WebshopMainOption option = (WebshopMainOption) KeyboardUtility.askForElementInArray(WebshopMainOption.values());
            switch (option) {
                case SEE_ALL_PRODUCTS -> startAllProducts(loggedInUser);
                case SEE_PRODUCTS_IN_BASKET -> startListAllProducts(loggedInUser);
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void startAllProducts(User loggedInUser) {
        System.out.println("All products, select one if you want to check more details");
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found");
        } else {
            Product selectedProduct = (Product) KeyboardUtility.askForElementInArray(products.toArray());
            System.out.printf("Product details: %s\n", selectedProduct.displayInfoBeforeDetails());
            selectProductOptionInWebShop(loggedInUser, selectedProduct);
        }
    }

    public void selectProductOptionInWebShop(User loggedInUser, Product selectedProduct) {
        System.out.println("Select operation with your products");
        boolean continueSelection = false;
        while (!continueSelection) {
            ProductInWebShopOption option = (ProductInWebShopOption) KeyboardUtility.askForElementInArray(ProductInWebShopOption.values());
            switch (option) {
                case ADD_PRODUCT_TO_BASKET -> startAddProductToBasket(loggedInUser, selectedProduct);
                case LIST_PRODUCTS_IN_BASKET -> startListAllProducts(loggedInUser);
                case REMOVE_PRODUCT_FROM_BASKET -> startRemoveProductFromBasket(loggedInUser, selectedProduct);
                case PROCEED_TO_BUY -> placeOrder(loggedInUser, selectedProduct);
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void placeOrder(User loggedInUser, Product selectedProduct) {
    }

    private void startRemoveProductFromBasket(User loggedInUser, Product selectedProduct) {
        System.out.printf("%s will be removed from basket\n", selectedProduct.getName());
        ShoppingCart shoppingCartOfLoggedInUser = shoppingCartService.findShoppingCartByUserId(loggedInUser.getId());
        cartItemService.removeProductFromBasket(selectedProduct, shoppingCartOfLoggedInUser);
        System.out.printf("%s was successfully removed\n", selectedProduct.getName());
    }

    private void startListAllProducts(User loggedInUser) {
        ShoppingCart shoppingCartOfLoggedInUser = shoppingCartService.findShoppingCartByUserId(loggedInUser.getId());
        List<CartItem> cartItems = cartItemService.findByShoppingCart(shoppingCartOfLoggedInUser);
        if (cartItems.isEmpty()) {
            System.out.println("No product item was found");
        } else {
            cartItems.forEach((cartItem -> {
                System.out.printf("Product %s\nQuantity: %s\n", cartItem.getProduct().getName(), cartItem.getQuantity());
                System.out.println();
            }));
        }

    }

    private void startAddProductToBasket(User loggedInUser, Product selectedProduct) {
        System.out.printf("Adding cart item %s to shopping cart:\n", selectedProduct.getName());
        int quantity = KeyboardUtility.askForInt(String.format("How many %s would you like to add to your basket\n", selectedProduct.getName()));
        ShoppingCart shoppingCartOfLoggedInUser = shoppingCartService.findShoppingCartByUserId(loggedInUser.getId());
        cartItemService.assignProductToCartItem(selectedProduct.getId(), shoppingCartOfLoggedInUser.getId(), quantity);
        System.out.printf("%s was successfully added to your basket\n", selectedProduct.getName());
    }
}
