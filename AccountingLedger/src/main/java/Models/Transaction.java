package Models;

import Utilities.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final LocalDateTime transactionDate;
    private final String description;
    private final String vendor;
    private final double amount;

    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.transactionDate = LocalDateTime.parse(date + " " + time, dateTimeFormatter);
    }

    public String formatToCsv() {
        String date = getTransactionDate().format(dateFormatter);
        String time = getTransactionDate().format(timeFormatter);
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

    public String formatToConsole() {
        String date = getTransactionDate().format(dateFormatter);
        String time = getTransactionDate().format(timeFormatter);
        String priceColor = getAmount() < 0 ? Utils.RED : Utils.GREEN;
        return date + "|" + time + "|" + description + "|" + vendor + "|" + priceColor + amount + Utils.RESET;
    }

    //getter

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }
}
