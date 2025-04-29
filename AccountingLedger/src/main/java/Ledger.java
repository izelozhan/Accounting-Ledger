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

}
