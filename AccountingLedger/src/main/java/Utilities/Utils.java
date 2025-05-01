package Utilities;


import Models.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {

    public static void printTitle(String title) {
        System.out.println("\n=== " + title + " ===");
    }
    public static void csvHeader(){
        System.out.println("date|time|description|vendor|amount");
    }
    public static void printTransactions (ArrayList<Transaction> result){
        if (result.isEmpty()) {
            System.out.println("You don't have any transactions.");
        } else {
            double total = 0;
            Utils.csvHeader();
            for (Transaction transaction : result) {
                System.out.println(transaction.formatToCsv());
                total = transaction.getAmount() + total;
            }

            System.out.printf("The total is: $%.2f%n" , total);
        }
    }

    static Scanner scanner = new Scanner(System.in);

    public static String getStringFromTerminal(String message) {
        if (!message.isEmpty()){
            System.out.println(message);
        }
        return scanner.nextLine();
    }

    public static String getDoubleFromTerminal(String message, boolean isRequired) {
        if (!message.isEmpty()){
            System.out.println(message);

        }
        while(true) {
            try
            {
                String input = scanner.nextLine();
                Double.parseDouble(input);
                return input;
            }
            catch(Exception e) {
                if (!isRequired){
                    return "";
                } else {
                    System.out.println("Invalid value, please enter again.");

                }
            }
        }
    }

    public static String getDateFromTerminal(String message, boolean isRequired) {
        if (!message.isEmpty()){
            System.out.println(message);

        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while(true) {
            try
            {
                String input = scanner.nextLine();
                LocalDate.parse(input, dateFormatter);
                return input;
            }
            catch(Exception e) {
                if (!isRequired){
                    return "";
                } else {
                    System.out.println("Invalid value, please enter again.");

                }
            }
        }
    }
}
