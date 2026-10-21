package org.example;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertTrue;
import java.util.List;


public class CustomerTest {

    Customer customer;
    Book testBook;

    @BeforeEach
    void setup() {
        customer = new Customer();
        testBook = new Book("Test Book", "Somebody", 50.0, "123456789", 10);

        Library.setBooks(List.of(testBook));
    }

    @Test
    public void testAddToShoppingCartInvalidIsbn() {
        Customer customer = new Customer();
        customer.addToShoppingCart("987654321", 1);
        assertTrue(customer.getCart().getCartItems().isEmpty());
    }
}


