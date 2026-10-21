package org.example;

import lombok.Getter;
import lombok.extern.java.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log
public class Library {
    @Getter
    private static List<Book> books = new ArrayList<>();

    public static void loadBooksFromFile() {
        books.clear();
        String filePath = "src/main/resources/BooksList.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(",");

                if (values.length >= 5) {
                    books.add(new Book(
                            values[0],
                            values[1],
                            Double.parseDouble(values[2]),
                            values[3],
                            Integer.parseInt(values[4])
                    ));
                }
            }
        } catch (IOException e) {
            log.warning("Error reading file: " + e.getMessage());
        }
    }

    public static void showList() {
        if (books.isEmpty()) {
            log.info("The book list is empty.");
            return;
        }

        StringBuilder allBooks = new StringBuilder();

        for (Book book : books) {
            allBooks.append("\uD83D\uDD35 Title: \"").append(book.getTitle())
                    .append("\", Author: ").append(book.getAuthor())
                    .append(", Price: ").append(String.format("%.2f", book.getPrice())).append("€")
                    .append(", ISBN: ").append(book.getIsbn())
                    .append(", Quantity: ").append(book.getQuantity())
                    .append(System.lineSeparator());
        }

        log.info("Info: Showing the list of books:\n" + allBooks);
    }

    public static void searchBooks(Scanner scanner) {
        if (books.isEmpty()) {
            log.info("No books available to search.");
            return;
        }

        String keyword = scanner.nextLine().toLowerCase().trim();

        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword) || book.getAuthor().toLowerCase().contains(keyword)) {
                log.info(" \u2705 Found: Title: \"" + book.getTitle()
                        + "\", Author: " + book.getAuthor()
                        + ", Price: " + book.getPrice()
                        + "€, ISBN: " + book.getIsbn()
                        + ", Quantity: " + book.getQuantity());
                found = true;
            }
        }

        if (!found) {
            log.info("\u274C No books found for: " + keyword);
        }
    }

    public static void updateBookQuantity(String isbn, int amountToAdd) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.setQuantity(book.getQuantity() + amountToAdd);
                book.resetPriceAfterRestock();
                log.info("\u2705 Updated quantity for \"" + book.getTitle() + "\" to: " + book.getQuantity());
                return;
            }
        }
        log.warning("\u274C Book with ISBN " + isbn + " not found.");
    }

    public static void addNewBook(Scanner scanner) {
        String title;
        while (true) {
            log.info("Enter book title:");
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                log.warning("\u274C Title cannot be empty. Try again.");
            } else {
                break;
            }
        }

        String author;
        while (true) {
            log.info("Enter author name:");
            author = scanner.nextLine().trim();
            if (author.isEmpty()) {
                log.warning("\u274C Author name cannot be empty. Try again.");
            } else {
                break;
            }
        }

        String isbn;
        while (true) {
            log.info("Enter ISBN:");
            isbn = scanner.nextLine().trim();
            if (isbn.isEmpty()) {
                log.warning("\u274C ISBN cannot be empty. Try again.");
                continue;
            }

            boolean exists = false;
            for (Book book : getBooks()) {
                if (book.getIsbn().equals(isbn)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                log.warning("\u274C A book with this ISBN already exists.");
            } else {
                break;
            }
        }

        double price;
        while (true) {
            log.info("Enter price:");
            String priceInput = scanner.nextLine().trim();
            try {
                price = Double.parseDouble(priceInput);
                if (price <= 0) {
                    log.warning("\u274C Price cannot be negative or zero. Try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                log.warning("\u274C Invalid price. Enter a valid number.");
            }
        }

        int quantity;
        while (true) {
            log.info("Enter quantity:");
            String quantityInput = scanner.nextLine().trim();
            try {
                quantity = Integer.parseInt(quantityInput);
                if (quantity <= 0) {
                    log.warning("\u274C Quantity cannot be negative or zero. Try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                log.warning("\u274C Invalid quantity. Enter a valid number.");
            }
        }

        Book newBook = new Book(title, author, price, isbn, quantity);
        getBooks().add(newBook);
        log.info("\u2705 Book successfully added: " + newBook);
    }

    public static void showPriceDevelopment(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                List<Double> history = book.getPriceHistory();
                log.info("\u26AA Price history for \"" + book.getTitle() + "\": " + history + "\n");
                return;
            }
        }
        log.warning("\u274C Book with ISBN " + isbn + " not found.");
    }

    static void setBooks(List<Book> newBooks) {
        books = newBooks;
    }
}
