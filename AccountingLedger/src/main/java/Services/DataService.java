package Services;

import Models.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataService {
    DateTimeFormatter defaultDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
        //format my date/time to compare transactions
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //single date and time

        ArrayList<Transaction> sortedTransactions = readAllTransactions();

        sortedTransactions.sort((transaction1, transaction2) -> {
            LocalDateTime date1 = LocalDateTime.parse(transaction1.getDate() + " " + transaction1.getTime(), formatter);
            LocalDateTime date2 = LocalDateTime.parse(transaction2.getDate() + " " + transaction2.getTime(), formatter);
            return date2.compareTo(date1);
        });
        return sortedTransactions;
    }

    public void saveTransaction(Transaction transaction) {
        File file = new File(filePath);

        boolean fileExits = file.exists();

        try {
            //append to the file by passing true so it doesn't generate the file again
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

//            boolean hasHeader = false;
//            String input;
//
//            while ((input = bufferedReader.readLine()) != null) {
//                String[] tokens = input.split("\\|");
//
//                if (tokens[0].equals("date")) {
//                    hasHeader = true;
//                    break;
//                }
//            }

            if (!fileExits) {
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

    //todo: change how filters work
    public ArrayList<Transaction> search(
            String description,
            String vendor,
            String amount,
            String startDate,
            String endDate,
            boolean isPayment,
            boolean isDeposit
    ) {
        ArrayList<Transaction> transactions = getSortedTransactions();
        ArrayList<Transaction> result = new ArrayList<>();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getLocalDate(defaultDateFormatter);

            if (isPayment) {
                if (transaction.getAmount() < 0) {
                    //it's ok because this is a payment
                    result.add(transaction);
                }
                continue;
            }
            if (isDeposit) {
                if (transaction.getAmount() > 0) {
                    result.add(transaction);
                }
                continue;

            }
            if (!description.isEmpty()) {
                String tDesc = transaction.getDescription().trim().toLowerCase();
                if (tDesc.equals(description)) {
                    result.add(transaction);
                    continue;
                }
            }
            if (!vendor.isEmpty()) {
                String tVend = transaction.getVendor().trim().toLowerCase();
                if (tVend.equals(vendor)) {
                    result.add(transaction);
                    continue;
                }
            }
            if (!amount.isEmpty()) {
                String tAmount = String.valueOf(transaction.getAmount());
                if (tAmount.equals(amount)) {
                    result.add(transaction);
                }
            }
            if (!startDate.isEmpty() && !endDate.isEmpty()) {
                LocalDate formattedStartDate = LocalDate.parse(startDate.trim(), defaultDateFormatter);
                //inclusive end date
                LocalDate formattedEndDate = LocalDate.parse(endDate.trim(), defaultDateFormatter).plusDays(1);

                if (transactionDate.isAfter(formattedStartDate) && transactionDate.isBefore(formattedEndDate)) {
                    result.add(transaction);
                }
            } else {
                if (!startDate.isEmpty()) {
                    LocalDate formattedStartDate = LocalDate.parse(startDate.trim(), defaultDateFormatter);

                    if (transactionDate.isAfter(formattedStartDate)) {
                        result.add(transaction);
                        continue;
                    }
                }
                if (!endDate.isEmpty()) {
                    LocalDate formattedEndDate = LocalDate.parse(endDate.trim(), defaultDateFormatter);

                    if (transactionDate.isBefore(formattedEndDate)) {
                        result.add(transaction);
                        continue;
                    }
                }
            }
        }

        return result;
    }
}
