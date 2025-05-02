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
 
![homescreen](https://github.com/user-attachments/assets/5268df21-cdc6-4898-a271-f88101cbc692)

**Ledger Screen** <br>

The Ledger Screen gives the user clear filter options to view all transactions, only deposits, or only payments—each sorted from newest to oldest for easy tracking. Users can quickly navigate between filters, access the Reports Screen for further insights, or return to the Home Screen with a single command.

![ledgerscreen](https://github.com/user-attachments/assets/7cc8373b-0222-448f-87a3-6ea4bb1046a6)

**Reports Screen** <br>

The Reports Screen allows users to filter transactions by date, vendor, or custom criteria like Start Date, End Date, Description, Vendor, and Amount. Reports can be generated for the current month, previous month, year-to-date, or previous year. Users can apply filters only on the fields they enter.

![reportsscreen](https://github.com/user-attachments/assets/5043900c-604e-4713-aad0-e83a147158f7)

---

## 🏃 How to Run 

- To get started clone the repository to your local:

  `git clone https://github.com/izelozhan/capstone-1.git`

- After cloning the repository, navigate into the project directory and run it.
