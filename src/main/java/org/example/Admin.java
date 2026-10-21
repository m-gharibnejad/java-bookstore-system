package org.example;

import lombok.extern.java.Log;
import java.util.Scanner;

@Log
public class Admin implements Role {
    private static final String PASSWORD = "admin123";

    @Override
    public void showMenu() {
        log.info("""
            Admin Menu:
            0. Return to role menu
            1. Show list of books
            2. Search
            3. Update book quantity
            4. Exit
            """);
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
                log.info("Please enter the ISBN of the book you want to increase the quantity: ");
                String isbn = scanner.nextLine().trim();

                int quantityToAdd;
                while (true) {
                    log.info("Enter how many copies you'd like to add: ");
                    String input = scanner.nextLine().trim();

                    try {
                        quantityToAdd = Integer.parseInt(input);
                        if (quantityToAdd <= 0) {
                            log.warning("\u274C Quantity must greater than zero, try again: ");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        log.warning("\u274C Invalid number format. ");
                    }
                }

                Library.updateBookQuantity(isbn, quantityToAdd);
                break;
            case 4:
                log.info("Happy reading! Goodbye.");
                System.exit(0);
                break;
            default:
                log.warning("\u274C Invalid choice, try again: ");
        }
    }

    @Override
    public boolean authenticate(String password) {
        return PASSWORD.equals(password);
    }
}



