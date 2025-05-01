package Screens;

import Models.Transaction;
import Services.DataService;
import Utilities.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HomeScreen {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    LedgerScreen ledger = new LedgerScreen();
    DataService dataService = new DataService();

    public void showHomeScreenOptionsMenu() {
        Utils.printTitle("ACCOUNTING LEDGER APPLICATION");
        System.out.println("Welcome to our Accounting Ledger Application! Select an option to start!");
        System.out.println("D: Add Deposit");
        System.out.println("P: Make Payment (Debit)");
        System.out.println("L: Ledger");
        System.out.println("X: Exit \n");
    }

    public String receiveUserOption() {
        //take user's option and assign it to a new String.
        String selectedOption = "INVALID";

        while (selectedOption.equals("INVALID")) {
            String userOption = Utils.getStringFromTerminal("Please choose one of the options.").toUpperCase();
            selectedOption = switch (userOption) {
                case "D" -> "ADD_DEPOSIT";
                case "P" -> "MAKE_PAYMENT";
                case "L" -> "LEDGER";
                case "X" -> "EXIT";
                default -> "INVALID";
            };
            if (selectedOption.equals("INVALID")) {
                Utils.printError("Invalid option. Please enter D, P, L or X.");
            }
        }
        return selectedOption;
    }

    public boolean performUserOption(String userOption) {
        if (userOption.equals("EXIT")) {
            return true; //so it can exit
        }
        switch (userOption) {
            case "ADD_DEPOSIT" -> addDeposit();
            case "MAKE_PAYMENT" -> makePayment();
            case "LEDGER" -> showLedgerScreen();
        }
        return false;
    }

    public Transaction createTransaction(boolean isPayment) {
        System.out.println("Please enter the following information:");

        String description = Utils.getStringFromTerminal("1) Enter description: ");
        String vendor = Utils.getStringFromTerminal("2) Enter vendor: ");
        String amountString = Utils.getDoubleFromTerminal("3) Enter amount: ", true);
        double amount = Double.parseDouble(amountString);

        if (isPayment && amount > 0) {
            amount = -amount;
        }

        LocalDate date = LocalDate.now(); //offline dates
        LocalTime time = LocalTime.now();

        String formattedDate = date.format(dateFormatter);
        String formattedTime = time.format(timeFormatter);

        return new Transaction(formattedDate, formattedTime, description, vendor, amount);
    }

    public void addDeposit() {
        Utils.printTitle("ADD DEPOSIT");
        Transaction transaction = createTransaction(false);
        dataService.saveTransaction(transaction);
    }

    public void makePayment() {
        Utils.printTitle("MAKE PAYMENT");
        Transaction transaction = createTransaction(true);
        dataService.saveTransaction(transaction);
    }


    public void showLedgerScreen() {
        boolean isExitFromLedger = false;
        while(!isExitFromLedger) {
            ledger.showLedgerScreenOptionsMenu();
            String userOption = ledger.receiveUserOption();
            isExitFromLedger = ledger.performUserOption(userOption);
        }
    }


}
