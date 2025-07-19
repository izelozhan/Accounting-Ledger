package Screens;

import java.util.ArrayList;
import java.util.List;

import Models.Transaction;
import Services.DataService;
import Services.TransactionDao;
import UserInterface.UI;
import Utilities.Utils;
import org.apache.commons.dbcp2.BasicDataSource;

public class LedgerScreen {
    private final ReportsScreen reports;
    private final DataService dataService;
	//Create new fields to use the transactionDao and to set up a datasource
	static BasicDataSource dataSource = new BasicDataSource();
	static TransactionDao transactionDao = new TransactionDao(dataSource);

    public LedgerScreen() {
        reports = new ReportsScreen();
        dataService = new DataService();
    }

	//Method to set the properties to connect to the accountledger_v2 database.
	public static void setDataSource() {
		dataSource.setUrl("jdbc:mysql://localhost:3306/accountingledger_v2");
		dataSource.setUsername("root"); //!<--- You will need to change this to whatever username you use for SQL
		dataSource.setPassword(System.getenv("SQL_PASSWORD")); //!<--- You will need to change this to whatever password you use for SQL
	}

    public String receiveUserOption() {
        //take user's option and assign it to a new String.
        UserInterface.UI.showLedgerScreenOptionsMenu();

        String selectedOption = "INVALID";
        String invalid = "INVALID";

        while (selectedOption.equals(invalid)) {
            String userOption = Utils.getStringFromTerminal("Please choose one of the options.").toUpperCase();
            selectedOption = switch (userOption) {
                case "A" -> "ALL";
                case "D" -> "DEPOSITS";
                case "P" -> "PAYMENTS";
                case "R" -> "REPORTS";
                case "H" -> "HOME";
                default -> invalid;
            };
            if (selectedOption.equals(invalid)) {
                Utils.printError("Invalid option. Please enter A, D, P, R or H.");
            }
        }
        return selectedOption;
    }

    public boolean performUserOption(String userOption) {
        if(userOption.equals("HOME")) {
            return true;
        }
        switch (userOption) {
            case "ALL" -> showAllTransactions();
            case "DEPOSITS" -> showOnlyDeposits();
            case "PAYMENTS" -> showOnlyPayments();
            case "REPORTS" -> showReportsScreen();
        }
        return false;
    }

    private void showAllTransactions() {
		//Call setDataSource method to get connection
		setDataSource();
//        ArrayList<Transaction> transactions = dataService.getSortedTransactions();

		//New List that uses the Dao instead of the dataService class
		List<Transaction> transactionList = transactionDao.getAll();

		//Check if list is not empty
		if(!transactionList.isEmpty()) {
			//for each transaction in list use Transaction.printData() method to display them in the console.
			for(Transaction transaction : transactionList) {
				transaction.printData();
			}
		} else {
			System.out.println("There are no transactions to display...");
		}
//        Utils.reportTitle("All Transactions");
//        Utils.printTransactions((ArrayList<Transaction>) transactionList);
    }

    private void showOnlyDeposits() {
        ArrayList<Transaction> deposits = dataService.search("","","","","",false,true);
        Utils.reportTitle("Deposits");
        Utils.printTransactions(deposits);
    }

    private void showOnlyPayments() {
        ArrayList<Transaction> payments = dataService.search("","","","","",true,false);
        Utils.reportTitle("Payments");
        Utils.printTransactions(payments);
    }

    private void showReportsScreen() {
        UI.showReportsScreenOptionsMenu();
        String userOption = reports.receiveUserOption();
        reports.performUserOption(userOption);
    }


}
