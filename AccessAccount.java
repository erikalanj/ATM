import java.io.*;
import java.util.*;
public class AccessAccount extends NewAccount {
  //hi

  private String username;

  private int accountID;

  private double totalTransactions;

  private ArrayList < String > transactions;

  private String dob;

  private static boolean headerWritten = false;

  public AccessAccount(String username, int accountID, String dob) {

    super(); // Call the constructor of the superclass (NewAccount)

    this.username = username;

    this.accountID = accountID;

    this.totalTransactions = 0.0;

    this.transactions = new ArrayList < > ();

    this.dob = dob;

  }

  // Getter method for accountID

  public int getAccountID() {

    return accountID;

  }

  public void deposit(String dob, double amount) {

    transactions.add(username + " " + accountID + " " + "deposit " +

      this.dob + " " + amount + " " + (totalTransactions + amount));

    totalTransactions += amount;

    updateBalanceSheet();

  }

  public void withdraw(String dob, double amount) {

    if (totalTransactions >= amount) {

      transactions.add(username + " " + accountID + " " + totalTransactions + " withdraw " +

        this.dob + " " + amount);

      totalTransactions -= amount;

      updateBalanceSheet();

    } else {

      System.out.println("Insufficient funds!");

    }

  }

  private void updateBalanceSheet() {

    try (FileWriter writer = new FileWriter("balanceSheet.txt", true)) {

      if (!headerWritten) {

        writer.write("Username AccountID Total Transactions DOB Balance\n ");

          headerWritten = true;

        }

        for (String transaction: transactions) {

          writer.write(transaction + " " + accountID + " " + dob + "\n");

        }

      } catch (IOException e) {

        e.printStackTrace();

      }

    }

    public static List < Transaction > parseTransactions() {

      List < Transaction > transactions = new ArrayList < > ();

      try (Scanner scanner = new Scanner(new File("balanceSheet.txt"))) {

        while (scanner.hasNextLine()) {

          String line = scanner.nextLine();

          String[] parts = line.split(" ");

          if (parts.length >= 7) {

            String name = parts[0];

            String type = parts[5];

            try {

              double amount = Double.parseDouble(parts[6]);

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
