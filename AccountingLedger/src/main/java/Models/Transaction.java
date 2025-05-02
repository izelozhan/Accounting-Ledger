package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    LocalDateTime transactionDate;
    String description;
    String vendor;
    double amount;

    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.transactionDate = LocalDateTime.parse(date + " " + time, dateTimeFormatter);
    }

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

    public String formatToCsv() {
        String date = getTransactionDate().format(dateFormatter);
        String time = getTransactionDate().format(timeFormatter);

        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

}
