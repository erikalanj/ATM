public class Account extends AccessAccount {

    private String name;
    private static String dob;
    private int accountID;

    public Account(String name, String dob) {
        super(name, NewAccount.generateAccountID(name), dob);
        this.name = name;
        this.dob = dob;

        if (checkAccount(name)) {
            System.out.println("Account already exists for this user.");
        } else {
            this.accountID = getAccountID();
            createAccount(name, dob);
            System.out.println("Account created successfully.");
        }
    }

    public Account(String name, String dob, double initialDeposit) {
        this(name, dob);
        deposit(initialDeposit);
    }

    public Account(String name, String password, String operation, double amount) {
        super(name, NewAccount.generateAccountID(name), dob);
        this.accountID = getAccountID();

        if (operation.equals("deposit")) {
            deposit(amount);
        } else if (operation.equals("withdraw")) {
            withdraw(amount);
        }
    }
}