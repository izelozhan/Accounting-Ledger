package Screens;

import Utilities.Utils;
import java.util.Scanner;

public class ReportsScreen {
    Scanner scanner = new Scanner(System.in);

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

    private void backToReports() {
    }

    private void searchByVendor() {
    }

    private void previousYearReport() {
    }

    private void yearToDateReport() {
    }

    private void previousMonthReport() {
    }

    private void monthToDateReport() {
    }




}
