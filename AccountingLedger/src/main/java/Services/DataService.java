package Services;

import Models.Transaction;
import Utilities.Utils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataService {
    static DateTimeFormatter defaultDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static String filePath = "src/main/resources/transactions.csv";

    private ArrayList<Transaction> readAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String input;

            while ((input = bufferedReader.readLine()) != null) {
                String[] parts = input.split("\\|");
                if (parts[0].equals("date")) {
                    continue;
                }

                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                transactions.add(new Transaction(date, time, description, vendor, amount));
            }

            bufferedReader.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    public ArrayList<Transaction> getSortedTransactions() {
        ArrayList<Transaction> sortedTransactions = readAllTransactions();

        sortedTransactions.sort((transaction1, transaction2) -> {
            return transaction2.getTransactionDate().compareTo(transaction1.getTransactionDate());
        });
        return sortedTransactions;
    }

    public void saveTransaction(Transaction transaction) {
        File file = new File(filePath);

        boolean fileExits = file.exists();

        try {
            //append to the file by passing true so it doesn't generate the file again
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

            if (!fileExits) {
                bufferedWriter.write("date|time|description|vendor|amount");
            }

            bufferedWriter.write(transaction.formatToCsv());
            bufferedWriter.newLine();

            Utils.printSuccess("\nTransaction saved success!");
            bufferedWriter.close();

        } catch (IOException e) {
            Utils.printError("Error: An unexpected error occurred. Try again.");
        }
    }

    public ArrayList<Transaction> search(
            String description,
            String vendor,
            String amount,
            String startDate,
            String endDate,
            boolean filterIsPayment,
            boolean filterIsDeposit
    ) {
        ArrayList<Transaction> transactions = getSortedTransactions();
        ArrayList<Transaction> result = new ArrayList<>();

        for (Transaction transaction : transactions) {
            //toLocalDate drops the time, only keeps date
            LocalDate transactionDate = transaction.getTransactionDate().toLocalDate();

            if (filterIsPayment) {
                boolean isTransactionPayment = transaction.getAmount() < 0;
                if (!isTransactionPayment) {
                    //skip
                    continue;
                }
            }
            if (filterIsDeposit) {
                boolean isTransactionDeposit = transaction.getAmount() > 0;
                if (!isTransactionDeposit) {
                    // skip
                    continue;
                }
            }
            if (!description.isEmpty()) {
                String tDesc = transaction.getDescription().trim().toLowerCase();
                if (!tDesc.equals(description)) {
                    continue;
                }
            }
            if (!vendor.isEmpty()) {
                String tVend = transaction.getVendor().trim().toLowerCase();
                if (!tVend.equals(vendor)) {
                    continue;
                }
            }
            if (!amount.isEmpty()) {
                String tAmount = String.valueOf(transaction.getAmount());
                if (!tAmount.equals(amount)) {
                    continue;
                }
            }
            if (!startDate.isEmpty() && !endDate.isEmpty()) {
                LocalDate formattedStartDate = LocalDate.parse(startDate.trim(), defaultDateFormatter);
                //inclusive end date
                LocalDate formattedEndDate = LocalDate.parse(endDate.trim(), defaultDateFormatter).plusDays(1);
                boolean startDateWithinRange = transactionDate.isEqual(formattedStartDate) || transactionDate.isAfter(formattedStartDate);
                boolean endDateWithinRange = transactionDate.isEqual(formattedEndDate) || transactionDate.isBefore(formattedEndDate);

                boolean isWithinRange = startDateWithinRange && endDateWithinRange;
                if (!isWithinRange) {
                    continue;
                }
            } else {
                if (!startDate.isEmpty()) {
                    LocalDate formattedStartDate = LocalDate.parse(startDate.trim(), defaultDateFormatter);
                    boolean isWithinRange = transactionDate.isAfter(formattedStartDate) || transactionDate.isEqual(formattedStartDate);
                    if (!isWithinRange) {
                        continue;
                    }
                }
                if (!endDate.isEmpty()) {
                    LocalDate formattedEndDate = LocalDate.parse(endDate.trim(), defaultDateFormatter);
                    boolean isWithinRange = transactionDate.isBefore(formattedEndDate) || transactionDate.isEqual(formattedEndDate);
                    if (!isWithinRange) {
                        continue;
                    }
                }
            }
            result.add(transaction);
        }
        return result;
    }
}
