package UserInterface;
import Utilities.Utils;

public class UI {

    public static void showHomeScreenOptionsMenu() {
        Utils.printTitle("ACCOUNTING LEDGER APPLICATION");

        System.out.println("Welcome to our Accounting Ledger Application! Select an option to start!");
        System.out.println("D: Add Deposit");
        System.out.println("P: Make Payment (Debit)");
        System.out.println("L: Ledger");
        System.out.println("X: Exit \n");
    }

    public static void showLedgerScreenOptionsMenu() {
        Utils.printTitle("LEDGER SCREEN");

        System.out.println("Select option to start: ");
        System.out.println("A: All");
        System.out.println("D: Deposits");
        System.out.println("P: Payments");
        System.out.println("R: Reports");
        System.out.println("H: Home \n");
    }

    public static void showReportsScreenOptionsMenu() {
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
}
