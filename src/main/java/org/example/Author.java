package org.example;
import java.util.Scanner;
import lombok.extern.java.Log;

@Log
public class Author implements Role {
    private static final String PASSWORD = "author123";

    @Override
    public void showMenu() {
        log.info("""
            Author Menu:
            0. Return to role menu
            1. Show list of books
            2. Search
            3. Add your own book
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
                Library.addNewBook(scanner);
                break;
            case 4:
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

