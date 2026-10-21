package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void testIncreasePriceByPercentage() {
        Book book = new Book("Test Book", "Somebody", 100.0, "123456789", 10);
        book.increasePriceByPercentage(10.0);
        assertEquals(110.0, book.getPrice(), 0.001);
    }

    @Test
    public void testIncreasePriceByPercentageShouldRemainSame() {
        Book book1 = new Book("Test Book", "Somebody", 100.0, "123456789", 5);
        book1.increasePriceByPercentage(0.0);
        assertEquals(100.0, book1.getPrice(), 0.001);
    }

    @Test
    public void testGetPriceHistoryCorrectValues() {
        Book book = new Book("Test Book", "Somebody", 20.0, "123456789", 10);

        book.recordSalePrice();
        book.increasePriceByPercentage(5.0);
        book.recordSalePrice();
        book.increasePriceByPercentage(10.0);
        book.recordSalePrice();

        assertEquals(3, book.getPriceHistory().size());
        assertEquals(20.0, book.getPriceHistory().get(0), 0.001);
        assertEquals(21.0, book.getPriceHistory().get(1), 0.001);
        assertEquals(23.1, book.getPriceHistory().get(2), 0.001);
    }
}
