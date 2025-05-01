package Screens;

import Models.Transaction;
import Services.DataService;
import Utilities.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ReportsScreen {
    Scanner scanner = new Scanner(System.in);
    DataService dataService = new DataService();
    DateTimeFormatter defaultDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void showReportsScreenOptionsMenu() {
        Utils.printTitle("REPORTS SCREEN");
        System.out.println("Select option to start: ");
        System.out.println("1) Month to Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year to Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("6) Custom Search");
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
                case "6" -> "CUSTOM_SEARCH";
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
            case "CUSTOM_SEARCH" -> customSearch();
            case "BACK" -> backToReports();
        }
    }

    private void monthToDateReport() {
        Utils.printTitle("Your Month to Date Report");

        LocalDate today = LocalDate.now();
        LocalDate startOfTheMonth = LocalDate.now().withDayOfMonth(1);

        String startDate = startOfTheMonth.format(defaultDateFormatter);
        String endDate = today.format(defaultDateFormatter);

        ArrayList<Transaction> result = dataService.search("", "", "", startDate, endDate);

        if (result.isEmpty()) {
            System.out.println("You don't have any transaction for this month.");
        } else {
            double total = 0;
            for (Transaction transaction : result) {
                System.out.println(transaction.formatToCsv());
                total = transaction.getAmount() + total;
            }

            System.out.printf("Your total for %s is: $%.2f%n", today.getMonth(), total);

        }

    }

    private void previousMonthReport() {
        Utils.printTitle("Your Previous Month Report");
        //previous month
        LocalDate today = LocalDate.now();
        LocalDate previousMonth = today.minusMonths(1);
        LocalDate previousMonthStart = previousMonth.withDayOfMonth(1);
        LocalDate previousMonthEnd = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()); //length of the month as a last month

        String startDate = previousMonthStart.format(defaultDateFormatter);
        String endDate = previousMonthEnd.format(defaultDateFormatter);

        ArrayList<Transaction> result = dataService.search("", "", "", startDate, endDate);

        if (result.isEmpty()) {
            System.out.println("You don't have any transaction for previous month.");
        } else {
            double total = 0;

            for (Transaction transaction : result) {
                System.out.println(transaction.formatToCsv());
                total = transaction.getAmount() + total;
            }
            System.out.printf("Your total for previous month is: $%.2f%n", total);
        }
    }

    private void yearToDateReport() {
        Utils.printTitle("Your Year to Date Report");

        LocalDate today = LocalDate.now();
        LocalDate startOfTheYear = LocalDate.now().withDayOfYear(1);

        String startDate = startOfTheYear.format(defaultDateFormatter);
        String endDate = today.format(defaultDateFormatter);

        ArrayList<Transaction> result = dataService.search("", "", "", startDate, endDate);

        if (result.isEmpty()) {
            System.out.println("You don't have any transaction for this year.");
        } else {
            double total = 0;

            for (Transaction transaction : result) {
                System.out.println(transaction.formatToCsv());
                total = transaction.getAmount() + total;
            }
            System.out.printf("Your total for %s is: $%.2f%n", today.getYear(), total);
        }
    }

    private void previousYearReport() {
        Utils.printTitle("Your Previous Year Report");
        //previous year
        LocalDate today = LocalDate.now();
        LocalDate previousYear = today.minusYears(1);
        LocalDate previousYearStart = previousYear.withDayOfYear(1);
        LocalDate previousYearEnd = previousYear.withDayOfYear(previousYear.lengthOfYear()); //length of the year as a last day of the year

        String startDate = previousYearStart.format(defaultDateFormatter);
        String endDate = previousYearEnd.format(defaultDateFormatter);

        ArrayList<Transaction> result = dataService.search("", "", "", startDate, endDate);

        if (result.isEmpty()) {
            System.out.println("You don't have any transaction for previous year.");
        } else {
            double total = 0;

            for (Transaction transaction : result) {
                System.out.println(transaction.formatToCsv());
                total = transaction.getAmount() + total;
            }
            System.out.printf("Your total for previous year is: $%.2f%n", total);
        }
    }

    private void searchByVendor() {
        Utils.printTitle("Search By Vendor");

        System.out.println("Please enter your search term: ");
        String searchTerm = scanner.nextLine().trim().toLowerCase();

        ArrayList<Transaction> foundTransactions = dataService.search("", searchTerm, "", "", "");

        if (foundTransactions.isEmpty()) {
            System.out.println("No results found.");
        } else {
            System.out.println("Search results: ");
            for (Transaction transaction : foundTransactions) {
                System.out.println(transaction.formatToCsv());
            }
        }

    }

    private void customSearch() {
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


        ArrayList<Transaction> customFoundTransactions = dataService.search(
                description, vendor, amountInput, startDate, endDate
        );

        if (customFoundTransactions.isEmpty()) {
            System.out.println("No results found.");
        } else {
            System.out.println("Search results: ");
            for (Transaction transaction : customFoundTransactions) {
                System.out.println(transaction.formatToCsv());
            }
        }

    }

    private void backToReports() {
    }


}
