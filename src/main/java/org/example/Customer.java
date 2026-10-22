package org.example;

import java.util.Map;
import java.util.Scanner;
import lombok.extern.java.Log;

@Log
public class Customer implements Role {
    private static final String PASSWORD = "customer123";

    private ShoppingCart cart = new ShoppingCart();

    @Override
    public void showMenu() {
        log.info("""
            Customer Menu:
            0. Return to role menu
            1. Show list of books
            2. Search
            3. Buy a book
            4. View Shopping cart
            5. Confirm purchase
            6. Show price development
            7. Exit
            """);
    }

    public void addToShoppingCart(String isbn, int quantity) {
        Book book = null;
        for (Book b : Library.getBooks()) {
            if (b.getIsbn().equals(isbn)) {
                book = b;
                break;
            }
        }

        if (book == null) {
            log.warning("\u274C Book with ISBN " + isbn + " not found.");
            return;
        }

        if (quantity <= 0) {
            log.warning("\u274C Quantity must be greater than zero.");
            return;
        }

        if (quantity > book.getQuantity()) {
            log.warning("\u274C Not enough stock. Available quantity: " + book.getQuantity());
            return;
        }

        cart.addBook(book, quantity);
        log.info("\u2705 Added to cart: " + book.getTitle() + " (x" + quantity + ")");
    }

    public void confirmPurchase(Scanner scanner) {
        if (cart.getCartItems().isEmpty()) {
            log.info(" \u26AA Your shopping cart is empty.");
            return;
        }

        log.info("Total: " + String.format("%.2f", cart.calculateTotalPrice()) + "€");
        log.info("Do you want to confirm the purchase? (yes/no)");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (!answer.equals("yes")) {
            log.info("Purchase cancelled.");
            return;
        }
        for (Map.Entry<Book, Integer> entry : cart.getCartItems().entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();

            if (book.getQuantity() < quantity) {
                log.warning("\u274C Not enough stock for \"" + book.getTitle() + "\". Available: " + book.getQuantity());
                return;
            }
        }

        for (Map.Entry<Book, Integer> entry : cart.getCartItems().entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();

            book.setQuantity(book.getQuantity() - quantity);
            book.increasePriceByPercentage(3);
            book.recordSalePrice();

            log.info("\u26AA " + book.getTitle() + " " + book.getPriceDevelopment() + "\n");
        }

        log.info("\u2705 Purchase confirmed. Thank you!");
        cart.clear();
    }

    public ShoppingCart getCart() {
        return cart;
    }

    @Override
    public void handleAction(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                Library.showList();
                break;
            case 2:
                while (true) {
                    log.info("Please enter the book title or author (for going back to the main menu, type \"back\"): ");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("back"))
                        break;
                    Library.searchBooks(new Scanner(input));
                }
                break;
            case 3:
                log.info("Please enter the ISBN of the book you want to add to the shopping cart: ");
                String isbn = scanner.nextLine().trim();

                log.info("Please enter the quantity: ");
                int quantity;
                try {
                    quantity = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    log.warning("\u274C Invalid quantity. Please enter a valid number. ");
                    break;
                }
                addToShoppingCart(isbn, quantity);
                break;
            case 4:
                cart.showCart();
                break;
            case 5:
                confirmPurchase(scanner);
                break;
            case 6:
                log.info("Enter ISBN of the book to view price development: ");
                String isbnForHistory = scanner.nextLine().trim();
                Library.showPriceDevelopment(isbnForHistory);
                break;
            case 7:
                log.info("Happy reading! Goodbye... ");
                System.exit(0);
                break;

            default:
                log.warning("\u274C Invalid choice");
        }
    }

    @Override
    public boolean authenticate(String password) {
        return PASSWORD.equals(password);
    }
}

