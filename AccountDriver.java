//convert text into a crv (excel) or a sql databse and use an api to retrieve this data, and then use a gui to allow a user to interact
// with the program
import java.util.Scanner;

public class AccountDriver {

    public static void main(String[] args) {
        String name, dob, pass;
        int ch, day, month, year;
        double amount;
        Scanner kb = new Scanner(System.in);
        Account acct;

        do {
            acct = null;
            System.out.println("Enter your choice (1-5):");
            System.out.print("\n 1. Create new account without deposit");
            System.out.print("\n 2. Create new account with deposit an amount");
            System.out.print("\n 3. Deposit an amount to an existing account");
            System.out.print("\n 4. Withdraw an amount from an account");
            System.out.print("\n 5. Exit");
            System.out.print("\n\n Enter your choice: ");
            ch = kb.nextInt();
            kb.nextLine();

            switch (ch) {
                case 1:
                    System.out.println("Enter name: ");
                    name = kb.nextLine();
                    if (new NewAccount().checkAccount(name)) {
                        System.out.println("Account already exists for this user.");
                        break;
                    }
                    do {
                        System.out.println("Enter date of birth as day month year(e.g. 23 12 2001):");
                        System.out.print("Day: ");
                        day = kb.nextInt();
                        System.out.print("Month: ");
                        month = kb.nextInt();
                        System.out.print("Year: ");
                        year = kb.nextInt();
                    } while (checkInput(day, month, year));
                    dob = (day + "/" + month + "/" + year);
                    acct = new Account(name, dob);
                    break;

                case 2:
                    System.out.println("Enter name: ");
                    name = kb.nextLine();
                    if (new NewAccount().checkAccount(name)) {
                        System.out.println("Account already exists for this user.");
                        break;
                    }
                    do {
                        System.out.println("Enter date of birth as day month year(e.g. 23 12 2001):");
                        System.out.print("Day: ");
                        day = kb.nextInt();
                        System.out.print("Month: ");
                        month = kb.nextInt();
                        System.out.print("Year: ");
                        year = kb.nextInt();
                    } while (checkInput(day, month, year));
                    dob = (day + "/" + month + "/" + year);
                    System.out.println("Enter initial deposit amount: ");
                    amount = kb.nextDouble();
                    acct = new Account(name, dob, amount);
                    break;

                case 3:
                    System.out.print("Enter name: ");
                    name = kb.nextLine();
                    if (!new NewAccount().checkAccount(name)) {
                        System.out.println("Account does not exist.");
                        break;
                    }
                    System.out.print("Enter password: ");
                    pass = kb.nextLine();
                    String hashedPassword = NewAccount.getHashedPassword(name);
                    if (hashedPassword == null || !hashedPassword.equals(NewAccount.hashPassword(pass))) {
                        System.out.println("Incorrect password.");
                        break;
                    }
                    System.out.print("Enter amount to be deposited: ");
                    amount = kb.nextDouble();
                    acct = new Account(name, pass, "deposit", amount);
                    break;

                case 4:
                    System.out.print("Enter name: ");
                    name = kb.nextLine();
                    if (!new NewAccount().checkAccount(name)) {
                        System.out.println("Account does not exist.");
                        break;
                    }
                    System.out.print("Enter password: ");
                    pass = kb.nextLine();
                    hashedPassword = NewAccount.getHashedPassword(name);
                    if (hashedPassword == null || !hashedPassword.equals(NewAccount.hashPassword(pass))) {
                        System.out.println("Incorrect password.");
                        break;
                    }
                    System.out.print("Enter amount you want to withdraw: ");
                    amount = kb.nextDouble();
                    acct = new Account(name, pass, "withdraw", amount);
                    break;

                case 5:
                    System.out.println("Exit.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (ch != 5);
        kb.close();
    }

    private static boolean checkInput(int day, int month, int year) {
        boolean checkInput = false;
        if (day <= 0 || day > 31) {
            System.out.println("Invalid day");
            checkInput = true;
        }
        if (month <= 0 || month > 12) {
            System.out.println("Invalid Month");
            checkInput = true;
        }
        return checkInput;
    }
}
