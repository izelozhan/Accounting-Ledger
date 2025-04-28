import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Home {
    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    boolean showScreen = true;

    public void showHomeScreenOptionsMenu() {
        System.out.println("=== ACCOUNTING LEDGER APPLICATION === \n");
        System.out.println("Welcome to our Accounting Ledger Application! Select an option to start!");
        System.out.println("D: Add Deposit");
        System.out.println("P: Make Payment (Debit)");
        System.out.println("L: Ledger");
        System.out.println("X: Exit \n");
    }

    public String receiveUserOption() {
        String userOption = "";
        //for getting user's option, call options menu method
        showHomeScreenOptionsMenu();
        //take user's option and assign it to a new String.
        userOption = scanner.nextLine().toUpperCase();

        switch (userOption) {
            case "D" -> addDeposit();
            case "P" -> makePayment();
            case "L" -> showLedgerScreen();
            case "X" -> exit();
            default -> System.out.println("Invalid option. Please enter D, P, L or X.");
        }
        return userOption;
    }

    public void addDeposit() {
        //for transaction.csv =>  date/time/description/vendor/amount
        System.out.println("\n=== Add Deposit ===");
        System.out.println("Please enter the following information:");

        System.out.println("1) Enter description: ");
        String description = scanner.nextLine();

        System.out.println("2) Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("3) Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String formattedDate = date.format(dateFormatter);
        String formattedTime = time.format(timeFormatter);

        //create new transaction
        Transaction transaction = new Transaction(formattedDate, formattedTime, description, vendor, amount);
        saveTransaction(transaction);

        askReturnHomeScreen();

    }

    public void askReturnHomeScreen(){
        boolean returnHome = true;

        while (returnHome) {
            System.out.println("\nDo you want to return Home Screen? (Yes/No)");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("yes")) {
                System.out.println("\nReturning to Home Screen!\n");
                receiveUserOption();
                returnHome = false;
            } else if (input.equalsIgnoreCase("no")) {
                exit();
                returnHome = false;
            } else {
                System.out.println("Invalid input. Please type 'Yes' or 'No'");
            }
        }
    }

    public void saveTransaction(Transaction transaction) {
        try {
            //append to the file by passing true so it doesn't generate the file again
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //csvFormat add pipe between to every item
            fileWriter.write(transaction.csvFormat());
            fileWriter.write("\n");
            System.out.println("\nTransaction success!");
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("Error: An unexpected error occurred. Try again.");
        }
    }

    public void showLedgerScreen() {
        System.out.println("Show ledger screen");
        showScreen = false;

    }

    public void makePayment() {
        System.out.println("Make Payment");
        showScreen = false;

    }

    public void exit() {
        System.out.println("See you next time!");
        showScreen = false;
    }

}
