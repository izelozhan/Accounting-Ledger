package Utilities;

import Models.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    static Scanner scanner = new Scanner(System.in);
    static String RESET = "\u001B[0m";
    static String RED = "\u001B[31m";
    static String GREEN = "\u001B[32m";
    static String YELLOW = "\u001B[33m";
    static String BLUE = "\u001B[34m";

    public static void printTitle(String title) {
        System.out.println(BLUE + "\n=== " + title + " ===" + RESET);
    }

    public static void printCsvHeader() {
        System.out.println("date|time|description|vendor|amount");
    }

    public static void printTransactions(ArrayList<Transaction> result) {
        if (result.isEmpty()) {
            System.out.println("You don't have any transactions.");
        } else {
            double total = 0;
            Utils.printCsvHeader();
            for (Transaction transaction : result) {
                System.out.println(transaction.formatToCsv());
                total = transaction.getAmount() + total;
            }

            System.out.printf("The total is: $%.2f%n", total);
        }
    }

    public static String getStringFromTerminal(String message) {
        if (!message.isEmpty()) {
            printInfo(message);
        }
        return scanner.nextLine();
    }

    public static String getDoubleFromTerminal(String message, boolean isRequired) {
        if (!message.isEmpty()) {
            printInfo(message);
        }
        while (true) {
            try {
                String input = scanner.nextLine();
                Double.parseDouble(input);
                return input;
            } catch (Exception e) {
                if (!isRequired) {
                    return "";
                } else {
                    printError("Invalid value, please enter again.");
                }
            }
        }
    }

    public static String getDateFromTerminal(String message, boolean isRequired) {
        if (!message.isEmpty()) {
            printInfo(message);
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                String input = scanner.nextLine();
                LocalDate.parse(input, dateFormatter);
                return input;
            } catch (Exception e) {
                if (!isRequired) {
                    return "";
                } else {
                    printError("Invalid value, please enter again.");
                }
            }
        }
    }

    public static void printError(String message) {
        System.out.println(RED + message + RESET);
    }

    public static void printSuccess(String message) {
        System.out.println(GREEN + message + RESET);
    }

    public static void printInfo(String message) {
        System.out.println(YELLOW + message + RESET);
    }


}
