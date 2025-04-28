import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Home {
    Scanner scanner = new Scanner(System.in);
    boolean showScreen = true;

    public void showOptionsMenu() {
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
        //for transaction.csv =>  date/time/description/vendor/amount
        System.out.println("=== Add Deposit ===");
        System.out.println("Please enter the following information:");

        System.out.println("Enter description: ");
        String description = scanner.nextLine();

        System.out.println("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        // date&time formatters
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String formattedDate = date.format(dateFormatter);
        String formattedTime = time.format(timeFormatter);

        //create new transaction
        Transaction transaction = new Transaction(formattedDate, formattedTime, description, vendor, amount);
        saveTransaction(transaction);


    }
    public void saveTransaction(Transaction transaction){
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //csvFormat add pipe between to every item
            fileWriter.write(transaction.csvFormat());

            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
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
