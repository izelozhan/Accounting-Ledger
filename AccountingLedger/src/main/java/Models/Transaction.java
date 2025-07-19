package Models;

import Utilities.Utils;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    LocalDateTime transactionDate;
    String description;
    String vendor;
    double amount;
	//Created new fields for data from SQL
	int transactionId;
	Date sqlDate; //<--- Created new field for date using java.sql.Date
	Time sqlTime; //<--- Created new field for time using java.sql.Time

	public Transaction(String date, String time, String description, String vendor, double amount) {
		this.description = description;
		this.vendor = vendor;
		this.amount = amount;
		this.transactionDate = LocalDateTime.parse(date + " " + time, dateTimeFormatter);
	}

	//Created new constructor so we can create Transaction object from database data
    public Transaction(int transactionId, Date date, Time time, String description, String vendor, double amount) {
		this.transactionId = transactionId;
		this.sqlDate = date;
		this.sqlTime = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
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

    public String formatToConsole() {
        String date = getTransactionDate().format(dateFormatter);
        String time = getTransactionDate().format(timeFormatter);
        String priceColor = getAmount() < 0 ? Utils.RED : Utils.GREEN;
        return date + "|" + time + "|" + description + "|" + vendor + "|" + priceColor + amount + Utils.RESET;
    }

	//Created new method to print data from SQL to the console
	//I know there is a method in Utils you've been using to print the data, but I felt making this was easier for me.
	public void printData() {
		String priceColor = getAmount() < 0 ? Utils.RED : Utils.GREEN;

		System.out.println("-----TRANSACTION-----");
		System.out.println("ID: " + this.transactionId);
		System.out.println("Date: " + this.sqlDate);
		System.out.println("Time: " + this.sqlTime);
		System.out.println("Description" + this.description);
		System.out.println("Vendor: " + this.vendor);
		System.out.printf("Amount: %s $%.2f %s", priceColor, this.amount, Utils.RESET);
		System.out.println("--------------------------------------------------");
	}

}
