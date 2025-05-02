# 💸 Capstone-1: Accounting Ledger CLI 💸

This is a **command-line interface (CLI)** application to manage and track financial transactions for business or personal use. The application reads and saves all transactions to a file named `transactions.csv` . Each transaction is recorded as a single line in the CSV file.

---

## ✨ Features

- ➕ Add new income or make payment  
- 📄 Display all transactions  
- 💵  Display only deposits/payments  
- 📆 Pre-defined reports by month/year  
- 🔍 Custom filter to search by any field  
- 📥 Load data from CSV file  

---

## 📁 CSV File Format

All transactions are stored in a file named `transactions.csv`. Each line represents a transaction with the following format:

`Date | Time | Description | Vendor | Amount`

## 📷 Application Screens

---

**Home Screen** <br>

The Home Screen gives the user a clear way to add deposits or payments by entering the description, vendor, and amount. It also allows switching to the Ledger to view transactions or exiting the app safely. 

![homescreen](https://github.com/user-attachments/assets/cee49e97-3f9e-434d-8fcf-da6b2bfd7fbf)

_**How to Add a Deposit/Make Payment?**_

To add a deposit/make a payment, follow these steps:

1. From Home Screen, select the option to add a deposit/make a payment.
2. Enter the description of the transaction.
3. Enter the vendor name.
4. Enter the amount to deposit.
5. Once all fields are completed, the application will save the transaction, display a success message and returns home screen.

   
![addDeposit](https://github.com/user-attachments/assets/c4d6d391-43f5-4ba5-8f2b-8c2a29c2cd0d)


**Ledger Screen** <br>

The Ledger Screen gives the user clear filter options to view all transactions, only deposits, or only payments—each sorted from newest to oldest for easy tracking. Users can quickly navigate between filters, access the Reports Screen for further insights, or return to the Home Screen with a single command.

![ledgerscreen](https://github.com/user-attachments/assets/7cc8373b-0222-448f-87a3-6ea4bb1046a6)

_**How can I see my deposits/payments?**_

To see your transactions, follow these steps:

1. From Home Screen, select the option to see Ledger Screen.
2. Choose All/Deposits or Payments.
3. Your transactions will be shown from newest to oldest in the following CSV format.
   
![2025-05-02 00_13_49-AccountingLedger – DataService](https://github.com/user-attachments/assets/b302a1c8-0a14-478a-afab-b6a964719f01)


**Reports Screen** <br>

The Reports Screen allows users to filter transactions by date, vendor, or custom criteria like Start Date, End Date, Description, Vendor, and Amount. Reports can be generated for the current month, previous month, year-to-date, or previous year. Users can apply filters only on the fields they enter.

![reportsscreen](https://github.com/user-attachments/assets/5043900c-604e-4713-aad0-e83a147158f7)

_**How it works?**_

1. **Month To Date** – Shows all transactions from the current month.
2. **Previous Month** – Shows all transactions from the last month.
3. **Year To Date** – Shows all transactions from the current year.
4. **Previous Year** – Shows all transactions from the last year.
5. **Search by Vendor** – You’ll be asked to enter a vendor name, and it will show all related transactions.
6. **Custom Search** – This option lets you search with more control. You can enter:
   - Start Date
   - End Date
   - Description
   - Vendor
   - Amount
   You can leave any of these fields blank. The system will only filter based on the fields you fill in.

![2025-05-02 00_16_40-AccountingLedger – DataService](https://github.com/user-attachments/assets/497fc87e-6a23-4a94-ae36-911495dee4a3)


---

## 🏃 How to Run 

- To get started clone the repository to your local:

  `git clone https://github.com/izelozhan/capstone-1.git`

- After cloning the repository, navigate into the project directory and run it.

---

## 💡 One Piece of Code 

Here is an interesting piece of code from the project: the `search()` method;

_**Why is interesting?**_

While building the transaction search functionality, I implemented the `searchByVendor` method for the project. While testing it, I started writing a custom search method where I prompted the user for different fields. I realized that I was already filtering by vendor again, as I continued, I noticed similar overlaps for date filtering and for the "only payments"/"only deposits" views on the ledger screen — all of them were basically filtering the same transaction list under different conditions.

At that point, I decided to bring everything together into one flexible method: search(). This method goes through all transactions, checks each one against the active filters (like whether the amount is negative or positive, the vendor or description matches, the date is in range, etc.), and only adds transactions that pass all the filters to the final result list.

To handle the date logic clearly, I used toLocalDate() to strip out the time and make reliable comparisons. Using continue statements helped keep the code clean by skipping over any transactions that failed an early filter.

This made the filtering logic much more maintainable and reusable across different screens, like the ledger view and other custom searches.

```
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
```



