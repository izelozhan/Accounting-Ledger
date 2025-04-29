import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Home {
    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    Ledger ledger = new Ledger();

    boolean showScreen = true;

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
            String userOption = scanner.nextLine().toUpperCase();
            selectedOption = switch (userOption) {
                case "D" -> "ADD_DEPOSIT";
                case "P" -> "MAKE_PAYMENT";
                case "L" -> "LEDGER";
                case "X" -> "EXIT";
                default -> "INVALID";
            };
            if (selectedOption.equals("INVALID")) {
                System.out.println("Invalid option. Please enter D, P, L or X.");
            }
        }
        return selectedOption;
    }

    public void performUserOption(String userOption) {
        switch (userOption) {
            case "ADD_DEPOSIT" -> addDeposit();
            case "MAKE_PAYMENT" -> makePayment();
            case "LEDGER" -> showLedgerScreen();
            case "EXIT" -> exit();
        }
    }

    public Transaction createTransaction(boolean isPayment) {
        System.out.println("Please enter the following information:");

        System.out.println("1) Enter description: ");
        String description = scanner.nextLine();

        System.out.println("2) Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("3) Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (isPayment && amount > 0) {
            amount = -amount;
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String formattedDate = date.format(dateFormatter);
        String formattedTime = time.format(timeFormatter);

        return new Transaction(formattedDate, formattedTime, description, vendor, amount);
    }

    public void addDeposit() {
        Utils.printTitle("ADD DEPOSIT");
        Transaction transaction = createTransaction(false);
        saveTransaction(transaction);
    }

    public void makePayment() {
        Utils.printTitle("MAKE PAYMENT");
        Transaction transaction = createTransaction(true);
        saveTransaction(transaction);
    }

    public void askReturnHomeScreen() {
        boolean returnHome = true;

        while (returnHome) {
            System.out.println("\nDo you want to return Home Screen? (Yes/No)");
            String input = scanner.nextLine().trim();

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
        String filePath = "src/main/resources/transactions.csv";
        File file = new File(filePath);

        try {
            //append to the file by passing true so it doesn't generate the file again
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            boolean hasHeader = false;
            String input;

            while ((input = bufferedReader.readLine()) != null) {
                String[] tokens = input.split("\\|");

                if (tokens[0].equals("date")) {
                    hasHeader = true;
                    break;
                }
            }

            if (!hasHeader) {
                bufferedWriter.write("date|time|description|vendor|amount");
            }

            bufferedWriter.write(transaction.formatToCsv());
            bufferedWriter.newLine();

            System.out.println("\nTransaction saved success!");
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("Error: An unexpected error occurred. Try again.");
        }
    }

    public void showLedgerScreen() {
        ledger.showLedgerScreenOptionsMenu();
        String userOption = ledger.receiveUserOption();
        ledger.performUserOption(userOption);

    }


    public void exit() {
        System.out.println("Exiting the application, see you next time!");
        showScreen = false;
    }

    public void terminate() {
        // https://stackoverflow.com/questions/12117160/terminate-a-console-application-in-java
        System.exit(0);
    }

}
