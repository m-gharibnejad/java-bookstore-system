package org.example;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@ToString
@Getter
@Setter
@EqualsAndHashCode(of = "isbn")
public class Book {
    private String title;
    private String author;
    private double price;
    private final double defaultPrice;
    private String isbn;
    private int quantity;
    private final LinkedList<Double> last3SalePrices = new LinkedList<>();

    public Book(String title, String author, double price, String isbn, int quantity) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.defaultPrice = price;
        this.isbn = isbn;
        this.quantity = quantity;
    }

    public void recordSalePrice() {
        if (last3SalePrices.size() == 3) {
            last3SalePrices.removeFirst();
        }
        last3SalePrices.add(price);
    }

    public void increasePriceByPercentage(double percent) {
        price *= 1 + (percent / 100);
    }

    public void resetPriceAfterRestock() {
        price = defaultPrice;
    }

    public List<Double> getPriceHistory() {
        return last3SalePrices;
    }

    public String getPriceDevelopment() {
        StringBuilder sb = new StringBuilder("Price development: ");
        for (Double p : last3SalePrices) {
            sb.append(String.format("%.2f€ ", p));
        }
        return sb.toString().trim();
    }
}
