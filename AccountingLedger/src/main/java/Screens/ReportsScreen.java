package Screens;

import Models.Transaction;
import Services.DataService;
import Utilities.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReportsScreen {
    final DataService dataService;
    final DateTimeFormatter defaultDateFormatter;

    public ReportsScreen() {
        dataService = new DataService();
        defaultDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }


    public void showReportsScreenOptionsMenu() {
        Utils.printTitle("REPORTS SCREEN");
        System.out.println("Select option to start: ");
        System.out.println("1) Month to Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year to Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("6) Custom Search");
        System.out.println("0) Back \n");
    }

    public String receiveUserOption() {
        //take user's option and assign it to a new String.
        String selectedOption = "INVALID";

        while (selectedOption.equals("INVALID")) {
            String userOption = Utils.getStringFromTerminal("Please choose one of the options.").toUpperCase();
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
                Utils.printError("Invalid option. Please enter 1, 2, 3, 4, 5 or 0.");
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
        }
    }

    private void monthToDateReport() {
        Utils.reportTitle("Your Month to Date Report");

        LocalDate today = LocalDate.now();
        LocalDate startOfTheMonth = LocalDate.now().withDayOfMonth(1);

        String startDate = startOfTheMonth.format(defaultDateFormatter);
        String endDate = today.plusDays(1).format(defaultDateFormatter);

        ArrayList<Transaction> result = dataService.search("", "", "", startDate, endDate, false, false);

        Utils.printTransactions(result);
    }

    private void previousMonthReport() {
        Utils.reportTitle("Your Previous Month Report");
        //previous month
        LocalDate today = LocalDate.now();
        LocalDate previousMonth = today.minusMonths(1);
        LocalDate previousMonthStart = previousMonth.withDayOfMonth(1);
        LocalDate previousMonthEnd = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()); //length of the month as a last month

        String startDate = previousMonthStart.format(defaultDateFormatter);
        String endDate = previousMonthEnd.format(defaultDateFormatter);

        ArrayList<Transaction> result = dataService.search("", "", "", startDate, endDate, false, false);

        Utils.printTransactions(result);

    }

    private void yearToDateReport() {
        Utils.reportTitle("Your Year to Date Report");

        LocalDate today = LocalDate.now();
        LocalDate startOfTheYear = LocalDate.now().withDayOfYear(1);

        String startDate = startOfTheYear.format(defaultDateFormatter);
        String endDate = today.format(defaultDateFormatter);

        ArrayList<Transaction> result = dataService.search("", "", "", startDate, endDate, false, false);

        Utils.printTransactions(result);
    }

    private void previousYearReport() {
        Utils.reportTitle("Your Previous Year Report");
        //previous year
        LocalDate today = LocalDate.now();
        LocalDate previousYear = today.minusYears(1);
        LocalDate previousYearStart = previousYear.withDayOfYear(1);
        LocalDate previousYearEnd = previousYear.withDayOfYear(previousYear.lengthOfYear()); //length of the year as a last day of the year

        String startDate = previousYearStart.format(defaultDateFormatter);
        String endDate = previousYearEnd.format(defaultDateFormatter);

        ArrayList<Transaction> result = dataService.search("", "", "", startDate, endDate, false, false);

        Utils.printTransactions(result);
    }

    private void searchByVendor() {
        String searchTerm = Utils.getStringFromTerminal("Please enter your search term: ").trim().toLowerCase();

        Utils.reportTitle("Search By Vendor");
        ArrayList<Transaction> result = dataService.search("", searchTerm, "", "", "", false, false);
        Utils.printTransactions(result);
    }

    private void customSearch() {
        Utils.reportTitle("Custom Search");

        System.out.println("Please enter your search term(s): ");

        String startDate = Utils.getDateFromTerminal("Enter Start Date (YYYY-MM-DD):", false).trim();
        String endDate = Utils.getDateFromTerminal("Enter End Date (YYYY-MM-DD): ", false).trim();
        String description = Utils.getStringFromTerminal("Enter Description: ").trim().toLowerCase();
        String vendor = Utils.getStringFromTerminal("Enter Vendor: ").trim().toLowerCase();
        String amountInput = Utils.getStringFromTerminal("Enter Amount: ").trim();


        ArrayList<Transaction> result = dataService.search(
                description, vendor, amountInput, startDate, endDate, false, false
        );

        Utils.printTransactions(result);
    }


}
