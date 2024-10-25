public class Account extends AccessAccount {

  private String name;

  private static String dob; // Consider making this final after initialization

  private int accountID;

  public Account(String name, String dob) {

      super(name, getLastAccountNumber(), dob);

      this.name = name;

      this.dob = dob;

      if (checkAccount(name)) {

          System.out.println("Account already exists for this user.");

      } else {

          createAccount(name, dob);

          this.accountID = getAccountID(); // Use the getter method to get accountID

          System.out.println("Account created successfully.");

      }

  }

  public Account(String name, String dob, double initialDeposit) {

      this(name, dob);

      deposit(initialDeposit);

  }

  public Account(String name, String password, String operation, double amount) {

      super(name, getLastAccountNumber(), dob); // Use this.dob to refer to the instance variable

      if (operation.equals("deposit")) {

          deposit(amount);

      } else if (operation.equals("withdraw")) {

          withdraw(amount);

      }

  }
}