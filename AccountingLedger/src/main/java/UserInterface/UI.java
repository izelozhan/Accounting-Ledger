package UI;

import Utilities.Utils;

import java.util.logging.Logger;

public class UserInterface {
    private static final Logger logger = Logger.getLogger(UserInterface.class.getName());

    public static void showHomeScreenOptionsMenu() {
        Utils.printTitle("ACCOUNTING LEDGER APPLICATION");

        logger.info("Welcome to our Accounting Ledger Application! Select an option to start!");
        logger.info("D: Add Deposit");
        logger.info("P: Make Payment (Debit)");
        logger.info("L: Ledger");
        logger.info("X: Exit \n");
    }

    public static void showLedgerScreenOptionsMenu() {
        Utils.printTitle("LEDGER SCREEN");

        logger.info("Select option to start: ");
        logger.info("A: All");
        logger.info("D: Deposits");
        logger.info("P: Payments");
        logger.info("R: Reports");
        logger.info("H: Home \n");
    }

}
