package Screens;

import Models.Transaction;
import Utilities.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ReportsScreen {
    Scanner scanner = new Scanner(System.in);
    LocalDate today = LocalDate.now();
    LocalDate startOfTheMonth = LocalDate.now().withDayOfMonth(1);
    LocalDate startOfTheYear = LocalDate.now().withDayOfYear(1);
    //previous month
    LocalDate previousMonth = today.minusMonths(1);
    LocalDate previousMonthStart = previousMonth.withDayOfMonth(1);
    LocalDate previousMonthEnd = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()); //length of the month as a last month
    //previous year
    LocalDate previousYear = today.minusYears(1);
    LocalDate previousYearStart = previousYear.withDayOfYear(1);
    LocalDate previousYearEnd = previousYear.withDayOfYear(previousYear.lengthOfYear()); //length of the year as a last day of the year

    public void showReportsScreenOptionsMenu() {
        Utils.printTitle("REPORTS SCREEN");
        System.out.println("Select option to start: ");
        System.out.println("1) Month to Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year to Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("0) Back");
    }

    public String receiveUserOption() {
        //take user's option and assign it to a new String.
        String selectedOption = "INVALID";

        while (selectedOption.equals("INVALID")) {
            String userOption = scanner.nextLine().toUpperCase();
            selectedOption = switch (userOption) {
                case "1" -> "MONTH_TO_DATE";
                case "2" -> "PREVIOUS_MONTH";
                case "3" -> "YEAR_TO_DATE";
                case "4" -> "PREVIOUS_YEAR";
                case "5" -> "SEARCH_BY_VENDOR";
                case "0" -> "BACK";
                default -> "INVALID";
            };
            if (selectedOption.equals("INVALID")) {
                System.out.println("Invalid option. Please enter 1, 2, 3, 4, 5 or 0.");
            }
        }
        return selectedOption;
    }

    public void performUserOption(String userOption) {
        switch (userOption) {
            case "MONTH_TO_DATE" -> monthToDateReport();
            case "PREVIOUS_MONTH" -> previousMonthReport();
            case "YEAR_TO_DATE" -> yearToDateReport();
            case "PREVIOUS_YEAR" -> previousYearReport();
            case "SEARCH_BY_VENDOR" -> searchByVendor();
            case "BACK" -> backToReports();
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

    private void monthToDateReport() {
        ArrayList<Transaction> transactions = getSortedTransactions();

        Utils.printTitle("Your Month to Date Report");

        boolean hasTransaction = false;
        double total = 0;

        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate());

            if (!date.isBefore(startOfTheMonth) && !date.isAfter(today)) {
                hasTransaction = true;
                System.out.println(transaction.formatToCsv());
                total = transaction.getAmount() + total;
            }
        }
        if (!hasTransaction) {
            System.out.println("You don't have any transaction for this month.");
        }
        System.out.printf("Your total for %s is: $%.2f%n", today.getMonth(), total);
    }

    private void previousMonthReport() {
        ArrayList<Transaction> transactions = getSortedTransactions();
        Utils.printTitle("Your Previous Month Report");

        boolean hasTransaction = false;
        double total = 0;

        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate());

            if (!date.isBefore(previousMonthStart) && !date.isAfter(previousMonthEnd)) {
                hasTransaction = true;
                System.out.println(transaction.formatToCsv());
                total = transaction.getAmount() + total;
            }
        }
        if (!hasTransaction) {
            System.out.println("You don't have any transaction for previous month.");
        }
        System.out.printf("Your total for previous month is: $%.2f%n", total);

    }

    private void yearToDateReport() {
        ArrayList<Transaction> transactions = getSortedTransactions();

        Utils.printTitle("Your Year to Date Report");

        boolean hasTransaction = false;
        double total = 0;

        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate());

            if (!date.isBefore(startOfTheYear) && !date.isAfter(today)) {
                hasTransaction = true;
                System.out.println(transaction.formatToCsv());
                total = transaction.getAmount() + total;
            }
        }
        if (!hasTransaction) {
            System.out.println("You don't have any transaction for this year.");
        }
        System.out.printf("Your total for %s is: $%.2f%n", today.getYear(), total);
    }

    private void previousYearReport() {
        ArrayList<Transaction> transactions = getSortedTransactions();

        Utils.printTitle("Your Previous Year Report");

        boolean hasTransaction = false;
        double total = 0;

        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate());

            if (!date.isBefore(previousYearStart) && !date.isAfter(previousYearEnd)) {
                hasTransaction = true;
                System.out.println(transaction.formatToCsv());
                total = transaction.getAmount() + total;
            }
        }
        if (!hasTransaction) {
            System.out.println("You don't have any transaction for previous year.");
        }
        System.out.printf("Your total for previous year is: $%.2f%n", total);
    }

    private void searchByVendor() {
        ArrayList<Transaction> transactions = getSortedTransactions();
        ArrayList<Transaction> foundTransactions = new ArrayList<>();

        Utils.printTitle("Search By Vendor");

        System.out.println("Please enter your search term: ");
        String searchTerm = scanner.nextLine().trim().toLowerCase();

        System.out.println("Search results: ");
        for (Transaction transaction : transactions){
            String vendor = transaction.getVendor().trim().toLowerCase();
            if (vendor.contains(searchTerm)){
                foundTransactions.add(transaction);
            }
        }

        if (foundTransactions.isEmpty()){
            System.out.println("No results found.");
        } else {
            for (Transaction transaction : foundTransactions){
                System.out.println(transaction.formatToCsv());
            }
        }

    }

    private void customSearch(){
        ArrayList<Transaction> transactions = getSortedTransactions();
        ArrayList<Transaction> customFoundTransactions = new ArrayList<>();

        Utils.printTitle("Custom Search");

        System.out.println("Please enter your search term(s): ");
        System.out.print("Enter Start Date (YYYY-MM-DD):");
        String startDate = scanner.nextLine().trim();

        System.out.print("Enter End Date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine().trim();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter Vendor: ");
        String vendor = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter Amount: ");
        String amountInput = scanner.nextLine().trim();

        System.out.println("Search results: ");
//        If the user enters a value for a field you should filter on that field
//•       If the user does not enter a value, you should not filter on that field
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        for (Transaction transaction : transactions){
            if (!startDate.isEmpty()){
                String date = transaction.getDate().trim();
                LocalDate transactionDate = LocalDate.parse(date, formatter);
                LocalDate formattedStartDate = LocalDate.parse(startDate.trim(), formatter);

                if (!transactionDate.isBefore(formattedStartDate) && !transactionDate.isAfter(today)){
                    customFoundTransactions.add(transaction);
                    continue;
                }
            }
            if (!endDate.isEmpty()){
                String date = transaction.getDate().trim();
                LocalDate transactionDate = LocalDate.parse(date, formatter);
                LocalDate formattedEndDate = LocalDate.parse(endDate.trim(), formatter);

                if (!transactionDate.isAfter(formattedEndDate)){
                    customFoundTransactions.add(transaction);
                    continue;
                }
            }
            if (!description.isEmpty()){
                String tDesc = transaction.getDescription().trim().toLowerCase();
                if (tDesc.equals(description)){
                    customFoundTransactions.add(transaction);
                    continue;
                }
            }
            if (!vendor.isEmpty()){
                String tVend = transaction.getVendor().trim().toLowerCase();
                if (tVend.equals(vendor)){
                    customFoundTransactions.add(transaction);
                    continue;
                }
            }
            if (!amountInput.isEmpty()){
                String tAmount = String.valueOf(transaction.getAmount());
                if (tAmount.equals(amountInput)){
                    customFoundTransactions.add(transaction);
                }
            }
        }



    }

    private void backToReports() {
    }


}
