package Screens;

import java.util.ArrayList;

import Models.Transaction;
import Services.DataService;
import UserInterface.UI;
import Utilities.Utils;

public class LedgerScreen {
    private final ReportsScreen reports;
    private final DataService dataService;

    public LedgerScreen() {
        reports = new ReportsScreen();
        dataService = new DataService();
    }


    public String receiveUserOption() {
        //take user's option and assign it to a new String.
        UserInterface.UI.showLedgerScreenOptionsMenu();

        String selectedOption = "INVALID";
        String invalid = "INVALID";

        while (selectedOption.equals(invalid)) {
            String userOption = Utils.getStringFromTerminal("Please choose one of the options.").toUpperCase();
            selectedOption = switch (userOption) {
                case "A" -> "ALL";
                case "D" -> "DEPOSITS";
                case "P" -> "PAYMENTS";
                case "R" -> "REPORTS";
                case "H" -> "HOME";
                default -> invalid;
            };
            if (selectedOption.equals(invalid)) {
                Utils.printError("Invalid option. Please enter A, D, P, R or H.");
            }
        }
        return selectedOption;
    }

    public boolean performUserOption(String userOption) {
        if(userOption.equals("HOME")) {
            return true;
        }
        switch (userOption) {
            case "ALL" -> showAllTransactions();
            case "DEPOSITS" -> showOnlyDeposits();
            case "PAYMENTS" -> showOnlyPayments();
            case "REPORTS" -> showReportsScreen();
        }
        return false;
    }

    private void showAllTransactions() {
        ArrayList<Transaction> transactions = dataService.getSortedTransactions();
        Utils.reportTitle("All Transactions");
        Utils.printTransactions(transactions);
    }

    private void showOnlyDeposits() {
        ArrayList<Transaction> deposits = dataService.search("","","","","",false,true);
        Utils.reportTitle("Deposits");
        Utils.printTransactions(deposits);
    }

    private void showOnlyPayments() {
        ArrayList<Transaction> payments = dataService.search("","","","","",true,false);
        Utils.reportTitle("Payments");
        Utils.printTransactions(payments);
    }

    private void showReportsScreen() {
        UI.showReportsScreenOptionsMenu();
        String userOption = reports.receiveUserOption();
        reports.performUserOption(userOption);
    }


}
