package Screens;

import java.util.ArrayList;
import java.util.Scanner;

import Models.Transaction;
import Services.DataService;
import Utilities.Utils;

public class LedgerScreen {
    Scanner scanner = new Scanner(System.in);
    ReportsScreen reports = new ReportsScreen();
    DataService dataService = new DataService();

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

    public void showAllTransactions() {
        ArrayList<Transaction> transactions = dataService.getSortedTransactions();
        Utils.addHeader();
        for (Transaction transaction : transactions) {
            System.out.println(transaction.formatToCsv());
        }
    }

    private void showOnlyDeposits() {
        ArrayList<Transaction> transactions = dataService.getSortedTransactions();
        Utils.addHeader();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction.formatToCsv());

            }
        }
    }

    private void showOnlyPayments() {
        ArrayList<Transaction> transactions = dataService.getSortedTransactions();
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
