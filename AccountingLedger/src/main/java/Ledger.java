import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Ledger {
    Scanner scanner = new Scanner(System.in);

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

        while(selectedOption.equals("INVALID")) {
            String userOption = scanner.nextLine().toUpperCase();
            selectedOption = switch (userOption) {
                case "A" -> "ALL";
                case "D" -> "DEPOSITS";
                case "P" -> "PAYMENTS";
                case "R" -> "REPORTS";
                case "H" -> "HOME";
                default -> "INVALID";
            };
            if(selectedOption.equals("INVALID")) {
                System.out.println("Invalid option. Please enter A, D, P, R or H.");
            }
        }
        return selectedOption;
    }

    public void performUserOption(String userOption) {
        switch (userOption) {
            case "A" -> showAllTransactions();
            case "D" -> showOnlyDeposits();
            case "P" -> showOnlyPayments();
            case "R" -> showReportsScreen();
            case "H" -> returnHome();
        }
    }

    private ArrayList<Transaction> readAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String filePath =  "src/main/resources/transactions.csv";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String input;

            while((input = bufferedReader.readLine()) != null){
                String[] parts = input.split("\\|");
                if (parts[0].equals("date")){
                    continue;
                }

                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                transactions.add(new Transaction(date, time, description, vendor,amount));
            }

            bufferedReader.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    private ArrayList<Transaction> sortedTransactions(){
        ArrayList<Transaction> sortedTransactions = readAllTransactions();
        sortedTransactions.sort((date1, date2) -> {
            String valueOfDate1 = date1.getDate();
            String valueOfDate2 = date2.getDate();
            return valueOfDate2.compareTo(valueOfDate1);
        });
        return sortedTransactions;
    }

    private void showAllTransactions() {
        ArrayList<Transaction> showAllTransactions = sortedTransactions();
        for (Transaction transaction : showAllTransactions){
            System.out.println(transaction);
        }

    }

    private void showOnlyDeposits() {
    }


    private void returnHome() {
    }

    private void showReportsScreen() {
    }

    private void showOnlyPayments() {
    }





}
