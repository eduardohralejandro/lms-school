package com.lms_schoolapp.greenteam.cui;

import com.lms_schoolapp.greenteam.cui.util.KeyboardUtility;
import com.lms_schoolapp.greenteam.model.*;
import com.lms_schoolapp.greenteam.service.CartItemService;
import com.lms_schoolapp.greenteam.service.OrderService;
import com.lms_schoolapp.greenteam.service.ProductService;
import com.lms_schoolapp.greenteam.service.ShoppingCartService;
import com.lms_schoolapp.greenteam.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebshopMainConsole {
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;
    private final OrderService orderService;

    public void selectOptionWebshop(User loggedInUser) {
        System.out.println("Select operation in web shop");
        boolean continueSelection = false;
        while (!continueSelection) {
            WebshopMainOption option = (WebshopMainOption) KeyboardUtility.askForElementInArray(WebshopMainOption.values());
            switch (option) {
                case SEE_ALL_PRODUCTS -> startAllProducts(loggedInUser);
                case SEE_PRODUCTS_IN_BASKET -> startListAllProducts(loggedInUser);
                case SEE_ALL_ORDERS -> startDisplayAllOrders(loggedInUser);
                case EXIT -> {
                    continueSelection = true;
                }
            }
        }
    }

    private void startDisplayAllOrders(User loggedInUser) {
        List<Order> findAllOrders = orderService.getOrdersWithProductDetails(loggedInUser.getId());
        if (findAllOrders.isEmpty()) {
            System.out.println("No orders found for the user.");
        } else {
            for (Order order : findAllOrders) {
                System.out.printf("Order Date: %s%n", order.getOrderDate());
                System.out.printf("Delivery Option: %s%n", order.getDeliveryOption());
                System.out.println("Products in this order:");

                for (CartItem cartItem : order.getCartItems()) {
                    Product product = cartItem.getProduct();
                    System.out.printf(" - Product Name: %s, Quantity: %d, Price: %.2f%n",
                            product.getName(),
                            cartItem.getQuantity(),
                            product.getPrice());
                }

                System.out.println("-----------------------------------------------------");
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
        System.out.println("Placing order, these are your items in your order: ");
        startListAllProducts(loggedInUser);
        System.out.println("Option delivery");
        DeliveryOption option = (DeliveryOption) KeyboardUtility.askForElementInArray(DeliveryOption.values());
        String askForContinue = KeyboardUtility.askForString("Are you sure do you want to place order (y/n): ");
        if (askForContinue.toLowerCase().equals("y")) {
            orderService.placeOrderByUserId(loggedInUser.getId(), option);
            System.out.println("Order was placed");
        } else {
            System.out.println("Buy process was canceled");
        }
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
