package org.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShoppingCartTest {
    @Test
    public void testCalculateTotalPrice() {
        ShoppingCart cart = new ShoppingCart();
        Book book1 = new Book("Test Book 1", "Author1", 10.0, "123456789", 1);
        Book book2 = new Book("Test Book 2", "Author2", 20.0, "987654321", 1);
        cart.addBook(book1, 2);
        cart.addBook(book2, 1);

        assertEquals(40.0, cart.calculateTotalPrice(), 0.001);
    }
}
