# AS Bookstore System
<img width="480" height="362" alt="TurnThePageGIFbyU S NationalArchives" src="https://github.com/user-attachments/assets/86d7067c-e51b-4ca8-9258-3ead0cb52011" />

![Java](https://img.shields.io/badge/Language-Java%2017+-orange?style=flat&logo=java)
![Build](https://img.shields.io/badge/Build-Maven-blue?style=flat&logo=apachemaven)
![Testing](https://img.shields.io/badge/Testing-JUnit%205-green?style=flat&logo=junit5)

A role based console application simulating an interactive bookstore system with real time dynamic price escalation algorithm.

<img width="1920" height="1080" alt="Video Project 1 (1)" src="https://github.com/user-attachments/assets/4837d63c-478f-4078-b51a-732dec95f62b" />


## Table of Contents
- [Background & Motivation](#background--motivation)
- [About The Project](#about-the-project)
- [Main Features](#main-features)
- [System Architecture](#system-architecture)
- [Key Code Implementations](#key-code-implementations)
- [Testing](#testing)
- [Getting Started](#getting-started)
- [Usage & Disclaimer](#usage--disclaimer)


## Background & Motivation
This project was originally inspired by a course assignment for the *Anwendungssysteme* module focused on simulating dynamic market price updates. Building on those concepts, I applied the logic to construct an interactive bookstore system featuring dynamic stock pricing mechanics. 

The core idea? **The rarer a book gets, the higher its price climbs.** It allows fellow book lovers to see which rare titles are truly worth investing in!


## About The Project
This project utilizes advanced Java concepts to model a dynamic buying and selling market. The system supports three primary user roles, each with distinct responsibilities:

* **Admin:** manages the library by viewing the list, searching for books, and updating
quantities when new stock arrives.
* **Author:** adds their unique books to the library and sells them.
* **Customer:** searches for books, adds them to a shopping cart, and confirms
purchases.

>  **A Quick Security Note:** The users in this application aren't exactly cybersecurity enthusiasts. Their passwords follow the convenient format role123 (for example, admin123, customer123, and author123). Please don't hire them to manage your production credentials).

> *These credentials are intended for demonstration purposes only and should never be reused in a real world application.*


## Main Features
- **Dynamic Price Escalation:** increases a book's current price by 3% after each confirmed purchase.
- **Price History:** tracks the three most recently recorded prices using `LinkedList<Double>`.
- **Restock Price Reset:** resets a book's price to its original default price when an admin restocks it.
- **Role-Based Access:** provides separate menus and operations for each user role.
- **Inventory Management:** supports book searches, stock updates, and CSV-based catalog loading.
- **Shopping Cart:** tracks selected books and quantities, validates stock availability, and calculates purchase totals.


## System Architecture
This project follows standard Object-Oriented principles, cleanly decoupling user roles, and entity models such as `Book`, `Library` and `ShoppingCart`

<img width="655" height="386" alt="image" src="https://github.com/user-attachments/assets/6cc4f0b2-82c0-4161-9ee0-b647f6850e8a" />


## Key Code Implementations

**1. Dynamic Price Escalation (`increasePriceByPercentage`)**
*File:* `Book.java` & `Customer.java`  
When a customer confirms a purchase, the system deducts inventory, inflates the book's price by 3%, and records the updated sale price into history:

```
// Inside Customer.java during purchase execution
for (Map.Entry<Book, Integer> entry : cart.getCartItems().entrySet()) {
    Book book = entry.getKey();
    int quantity = entry.getValue();

    book.setQuantity(book.getQuantity() - quantity);
    book.increasePriceByPercentage(3);
    book.recordSalePrice();
}

// Inside Book.java
public void increasePriceByPercentage(double percent) {
    price *= 1 + (percent / 100);
}
```

**2. Market Stabilization on Restock (`resetPriceAfterRestock`)**
*Files:* `Library.java` & `Book.java`

When an admin adds stock for a book, the library updates the quantity and automatically resets the inflated price back to its original price:

```
// Inside Library.java
public static void updateBookQuantity(String isbn, int amountToAdd) {
    for (Book book : books) {
        if (book.getIsbn().equals(isbn)) {
            book.setQuantity(book.getQuantity() + amountToAdd);
            book.resetPriceAfterRestock();
            log.info("Updated quantity for \"" + book.getTitle() + "\" to: " + book.getQuantity());
            return;
        }
    }
    log.warning("Book with ISBN " + isbn + " not found.");
}

// Inside Book.java
public void resetPriceAfterRestock() {
    price = defaultPrice;
}
```

**3. Price History Tracking & Display (`showPriceDevelopment`)**
*Files*: `Library.java` & `Book.java`

To show dynamic price trends over time, `Book` stores the last 3 transaction prices using a LinkedList<Double>. The Library queries this history by ISBN:

```
// Inside Book.java
public List<Double> getPriceHistory() {
    return last3SalePrices;
}

// Inside Library.java
public static void showPriceDevelopment(String isbn) {
    for (Book book : books) {
        if (book.getIsbn().equals(isbn)) {
            List<Double> history = book.getPriceHistory();
            log.info("Price history for \"" + book.getTitle() + "\": " + history + "\n");
            return;
        }
    }
    log.warning("Book with ISBN " + isbn + " not found.");
}
```

## Testing

The project uses JUnit 5 for testing. Run the tests from the project root directory using Maven:

```bash
mvn test
```


## Getting Started

**Prerequisites**
- Java JDK 17 or higher
- Apache Maven
- IntelliJ IDEA or Visual Studio Code

**Installation**
Clone the repository:

```
git clone https://github.com/m-gharibnejad/java-bookstore-system.git
cd java-bookstore-system
```

Open the project in your preferred IDE as a Maven project.

**Run the Application**

Run the `main()` method in:

`src/main/java/org/example/Main.java`



# Usage & Disclaimer
This repository is a personal project developed out of genuine interest in Java, OOP, and the logic behind dynamic pricing systems.

It began as a university assignment and grew into an opportunity to explore role-based application design, inventory management, collections, and purchase workflows.
The project is intended for **educational and demonstration purposes**. Its simplified authentication and pricing mechanisms are not designed for production use.

And finally, of course, no actual books were subjected to aggressive price inflation during development.
