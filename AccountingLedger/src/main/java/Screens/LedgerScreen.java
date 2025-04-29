package Screens;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import Models.Transaction;
import Utilities.Utils;

public class LedgerScreen {
    Scanner scanner = new Scanner(System.in);
    ReportsScreen reports = new ReportsScreen();

    //all entries should show the newest first,'
    public void showLedgerScreenOptionsMenu() {
        Utils.printTitle("LEDGER SCREEN");
        System.out.println("Select option to start: ");
        System.out.println("A: All");
        System.out.println("D: Deposits");
        System.out.println("P: Payments");
        System.out.println("R: Reports");
        System.out.println("H: Home");
    }

    public String receiveUserOption() {
        //take user's option and assign it to a new String.
        String selectedOption = "INVALID";

        while (selectedOption.equals("INVALID")) {
            String userOption = scanner.nextLine().toUpperCase();
            selectedOption = switch (userOption) {
                case "A" -> "ALL";
                case "D" -> "DEPOSITS";
                case "P" -> "PAYMENTS";
                case "R" -> "REPORTS";
                case "H" -> "HOME";
                default -> "INVALID";
            };
            if (selectedOption.equals("INVALID")) {
                System.out.println("Invalid option. Please enter A, D, P, R or H.");
            }
        }
        return selectedOption;
    }

    public void performUserOption(String userOption) {
        switch (userOption) {
            case "ALL" -> showAllTransactions();
            case "DEPOSITS" -> showOnlyDeposits();
            case "PAYMENTS" -> showOnlyPayments();
            case "REPORTS" -> showReportsScreen();
            case "HOME" -> returnHome();
        }
    }

    private ArrayList<Transaction> readAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String filePath = "src/main/resources/transactions.csv";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String input;

            while ((input = bufferedReader.readLine()) != null) {
                String[] parts = input.split("\\|");
                if (parts[0].equals("date")) {
                    continue;
                }

                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                transactions.add(new Transaction(date, time, description, vendor, amount));
            }

            bufferedReader.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    private ArrayList<Transaction> getSortedTransactions() {
        //format my date/time to compare transactions
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //single date and time

        ArrayList<Transaction> sortedTransactions = readAllTransactions();

        sortedTransactions.sort((transaction1, transaction2) -> {
            LocalDateTime date1 = LocalDateTime.parse(transaction1.getDate() + " " + transaction1.getTime(), formatter);
            LocalDateTime date2 = LocalDateTime.parse(transaction2.getDate() + " " + transaction2.getTime(), formatter);
            return date2.compareTo(date1);
        });
        return sortedTransactions;
    }

    public void showAllTransactions() {
        ArrayList<Transaction> transactions = getSortedTransactions();
        Utils.addHeader();
        for (Transaction transaction : transactions) {
            System.out.println(transaction.formatToCsv());
        }

    }

    private void showOnlyDeposits() {
        ArrayList<Transaction> transactions = getSortedTransactions();
        Utils.addHeader();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction.formatToCsv());

            }
        }
    }

    private void showOnlyPayments() {
        ArrayList<Transaction> transactions = getSortedTransactions();
        Utils.addHeader();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction.formatToCsv());
            }
        }
    }

    private void returnHome() {

    }

    private void showReportsScreen() {
        reports.showReportsScreenOptionsMenu();
        String userOption = reports.receiveUserOption();
        reports.performUserOption(userOption);
    }


}
