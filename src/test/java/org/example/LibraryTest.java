package org.example;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static junit.framework.TestCase.assertEquals;

public class LibraryTest {

    @BeforeEach
    void setup() {
        Library.getBooks().clear();
    }

    @Test
    public void testUpdateBookQuantity() {
        Book book = new Book("TestBook", "TestAuthor", 10.0, "1234", 5);
        Library.getBooks().add(book);

        Library.updateBookQuantity("1234", 3);

        assertEquals(8, book.getQuantity());
    }
}

