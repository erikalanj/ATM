import java.io.*;
import java.util.*;

public class AccessAccount extends NewAccount {

    private String username;
    private int accountID;
    private double totalTransactions;
    private ArrayList<String> transactions;
    private String dob;

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
        String transaction = String.format("%-10s %-10d %-10s %-15s %-15.2f %-15.2f", username, accountID, dob,
                "Deposit",
                amount, totalTransactions);
        try {
            transactions.add(CryptoUtil.encryptAES(transaction, aesKey, iv));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        updateBalanceSheet();
    }

    public void withdraw(double amount) {
        if (totalTransactions >= amount) {
            totalTransactions -= amount;
            String transaction = String.format("%-10s %-10d %-10s %-15s %-15.2f %-15.2f", username, accountID, dob,
                    "Withdraw", amount, totalTransactions);
            try {
                transactions.add(CryptoUtil.encryptAES(transaction, aesKey, iv));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            updateBalanceSheet();
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    private void updateBalanceSheet() {
        try (FileWriter writer = new FileWriter("balanceSheet.txt", true)) {
            for (String transaction : transactions) {
                writer.write(transaction + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String decryptedTransaction;

    public void decryptAndDisplayTransactions() {
        System.out.println("Decrypted Transactions:");
        for (String encryptedTransaction : transactions) {
            try {
                decryptedTransaction = CryptoUtil.decryptAES(encryptedTransaction, aesKey, iv);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(decryptedTransaction);
        }
    }
}
