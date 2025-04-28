import java.util.Scanner;

public class Home {
    Scanner scanner = new Scanner(System.in);
    boolean showScreen = true;

    public void showOptionsMenu(){
        System.out.println("Welcome to our Accounting Ledger Application! Select an option to start!: ");
        System.out.println("D: Add Deposit");
        System.out.println("P: Make Payment (Debit)");
        System.out.println("L: Ledger");
        System.out.println("X: Exit");
    }

    public String receiveUserOption() {
        String userOption = "";

        while (showScreen) {
            //for getting user's option, call options menu method
            showOptionsMenu();
            //take user's option and assign it to a new String.
            userOption = scanner.nextLine().toUpperCase();

            switch (userOption) {
                case "D" -> addDeposit();
                case "P" -> makePayment();
                case "L" -> showLedgerScreen();
                case "X" -> exit();
                default -> System.out.println("Invalid option. Please enter D, P, L or X.");
            }
        }
        return userOption;
    }
    public void addDeposit() {
        //for transaction.csv I need date/time/description/vendor/amount

    }

    public void showLedgerScreen() {
        System.out.println("Show ledger screen");
        showScreen = false;

    }

    public void makePayment() {
        System.out.println("Make Payment");
        showScreen = false;

    }

    public void exit(){
        showScreen = false;
    }

}
