import java.io.*;
import java.util.*;

public class AccessAccount extends NewAccount {

    private String username;
    private int accountID;
    private double totalTransactions;
    private ArrayList<String> transactions;
    private String dob;
    private static boolean headerWritten = false;

    public AccessAccount(String username, int accountID, String dob) {
        super();
        this.username = username;
        this.accountID = accountID;
        this.dob = dob;
        this.totalTransactions = retrieveLastBalance();
        this.transactions = new ArrayList<>();
    }

    public int getAccountID() {
        return accountID;
    }

    private double retrieveLastBalance() {
        double lastBalance = 0.0;
        try (Scanner scanner = new Scanner(new File("balanceSheet.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 6 && parts[0].equals(username) && Integer.parseInt(parts[1]) == accountID) {
                    lastBalance = Double.parseDouble(parts[5]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lastBalance;
    }

    public void deposit(double amount) {
        totalTransactions += amount;
        transactions.add(String.format("%-10s %-10d %-10s %-15s %-15.2f %-15.2f", username, accountID, dob, "Deposit",
                amount, totalTransactions));
        updateBalanceSheet();
    }

    public void withdraw(double amount) {
        if (totalTransactions >= amount) {
            totalTransactions -= amount;
            transactions.add(String.format("%-10s %-10d %-10s %-15s %-15.2f %-15.2f", username, accountID, dob,
                    "Withdraw", amount, totalTransactions));
            updateBalanceSheet();
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    private void updateBalanceSheet() {
        try (FileWriter writer = new FileWriter("balanceSheet.txt", true)) {
            if (!headerWritten) {
                writer.write("\n\n" + String.format("%-10s %-10s %-10s %-15s %-15s %-15s\n", "Name", "AccountID",
                        "Birthday", "Transaction Type", "Amount", "Total Balance"));
                headerWritten = true;
            }
            for (String transaction : transactions) {
                writer.write(transaction + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Transaction> parseTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("balanceSheet.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length >= 6) {
                    String name = parts[0];
                    String type = parts[3];
                    try {
                        double amount = Double.parseDouble(parts[4]);
                        transactions.add(new Transaction(name, type, amount));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static class Transaction {
        private String name;
        private String type;
        private double amount;

        public Transaction(String name, String type, double amount) {
            this.name = name;
            this.type = type;
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }
    }
}
