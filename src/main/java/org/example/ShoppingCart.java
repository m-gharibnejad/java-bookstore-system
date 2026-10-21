package org.example;

import lombok.extern.java.Log;

import java.util.HashMap;
import java.util.Map;

@Log
public class ShoppingCart {
    private Map<Book, Integer> cartItems = new HashMap<>();

    public void addBook(Book book, int quantity) {
        if (cartItems.containsKey(book)) {
            int currentQuantity = cartItems.get(book);
            cartItems.put(book, currentQuantity + quantity);
        } else {
            cartItems.put(book, quantity);
        }
    }

    public Map<Book, Integer> getCartItems() {
        return cartItems;
    }

    public void showCart() {
        if (cartItems.isEmpty()) {
            log.info("\u26AA Your shopping cart is empty.");
            return;
        }
        StringBuilder sb = new StringBuilder("\u26AA Shopping Cart Contents:\n");
        for (Map.Entry<Book, Integer> entry : cartItems.entrySet()) {
            sb.append(String.format("\"%s\" by %s - Quantity: %d - Price each: %.2f€\n", entry.getKey().getTitle(), entry.getKey().getAuthor(), entry.getValue(), entry.getKey().getPrice()));
        }
        sb.append(String.format("Total Price: %.2f€", calculateTotalPrice()));
        log.info(sb.toString());
    }

    public double calculateTotalPrice() {
        double total = 0.0;
        for (Map.Entry<Book, Integer> entry : cartItems.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void clear() {
        cartItems.clear();
    }
}


