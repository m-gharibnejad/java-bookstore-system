package org.example;

import lombok.extern.java.Log;

import java.util.Scanner;

@Log
public class Main {
    public static void main(String[] args) {
        Library.loadBooksFromFile();
        Scanner scanner = new Scanner(System.in);

        log.info("Welcome to \"AS\" Library! Choose your role: admin, author, customer:");
        while (true) {
            String roleInput = scanner.nextLine().toLowerCase().trim();

            // https://softwaremill.com/java-21-switch-the-power-on/
            Role role = switch (roleInput) {
                case "admin" -> new Admin();
                case "author" -> new Author();
                case "customer" -> new Customer();
                default -> null;
            };

            // Unicode from https://unicodeplus.com/
            if (role == null) {
                log.warning("\u274C Unknown role, try again.");
                continue;
            }

            // Password loop
            while (true) {
                log.info("Enter password:");
                String password = scanner.nextLine();

                if (role.authenticate(password)) {
                    break;
                } else {
                    log.warning("\u274C Wrong password! Try again.");
                }
            }

            // Role menu loop
            while (true) {
                role.showMenu();
                log.info("Choose an option: ");
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    log.warning("\u274C Please enter a valid number.");
                    continue;
                }

                if (choice == 0) {
                    log.info("Choose your role: admin, author, customer:");
                    break;
                }

                role.handleAction(choice, scanner);
            }
        }
    }
}



