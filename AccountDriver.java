import java.util.Scanner;

public class AccountDriver {

    public static void main(String[] args) {
        String name, dob, pass;
        int choice, day, month, year;
        double amount;
        Scanner scanner = new Scanner(System.in);
        Account account;

        do {
            account = null;
            System.out.println("\n--- Account Management System ---");
            System.out.println("1. Create new account without deposit");
            System.out.println("2. Create new account with deposit");
            System.out.println("3. Deposit to an existing account");
            System.out.println("4. Withdraw from an account");
            System.out.println("5. View transactions (decrypted)");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    name = scanner.nextLine();
                    if (new NewAccount().checkAccount(name)) {
                        System.out.println("Account already exists for this user.");
                        break;
                    }
                    dob = getDateOfBirth(scanner);
                    account = new Account(name, dob);
                    break;

                case 2:
                    System.out.print("Enter name: ");
                    name = scanner.nextLine();
                    if (new NewAccount().checkAccount(name)) {
                        System.out.println("Account already exists for this user.");
                        break;
                    }
                    dob = getDateOfBirth(scanner);
                    System.out.print("Enter initial deposit amount: ");
                    amount = scanner.nextDouble();
                    account = new Account(name, dob, amount);
                    break;

                case 3:
                    System.out.print("Enter name: ");
                    name = scanner.nextLine();
                    if (!new NewAccount().checkAccount(name)) {
                        System.out.println("Account does not exist.");
                        break;
                    }
                    System.out.print("Enter password: ");
                    pass = scanner.nextLine();
                    String hashedPassword = NewAccount.getHashedPassword(name);
                    if (hashedPassword == null || !hashedPassword.equals(NewAccount.hashPassword(pass))) {
                        System.out.println("Incorrect password.");
                        break;
                    }
                    System.out.print("Enter amount to be deposited: ");
                    amount = scanner.nextDouble();
                    account = new Account(name, pass, "deposit", amount);
                    break;

                case 4:
                    System.out.print("Enter name: ");
                    name = scanner.nextLine();
                    if (!new NewAccount().checkAccount(name)) {
                        System.out.println("Account does not exist.");
                        break;
                    }
                    System.out.print("Enter password: ");
                    pass = scanner.nextLine();
                    hashedPassword = NewAccount.getHashedPassword(name);
                    if (hashedPassword == null || !hashedPassword.equals(NewAccount.hashPassword(pass))) {
                        System.out.println("Incorrect password.");
                        break;
                    }
                    System.out.print("Enter amount to withdraw: ");
                    amount = scanner.nextDouble();
                    account = new Account(name, pass, "withdraw", amount);
                    break;

                case 5:
                    System.out.print("Enter name: ");
                    name = scanner.nextLine();
                    if (!new NewAccount().checkAccount(name)) {
                        System.out.println("Account does not exist.");
                        break;
                    }
                    System.out.print("Enter password: ");
                    pass = scanner.nextLine();
                    hashedPassword = NewAccount.getHashedPassword(name);
                    if (hashedPassword == null || !hashedPassword.equals(NewAccount.hashPassword(pass))) {
                        System.out.println("Incorrect password.");
                        break;
                    }
                    System.out.println("\n--- Transactions ---");
                    AccessAccount acc = new AccessAccount(name, NewAccount.generateAccountID(name), ""); // DOB is not
                                                                                                         // required
                                                                                                         // here
                    acc.decryptAndDisplayTransactions();
                    break;

                case 6:
                    System.out.println("Exiting. Thank you for using the system.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static String getDateOfBirth(Scanner scanner) {
        int day, month, year;
        do {
            System.out.println("Enter date of birth as day month year (e.g., 23 12 2001):");
            System.out.print("Day: ");
            day = scanner.nextInt();
            System.out.print("Month: ");
            month = scanner.nextInt();
            System.out.print("Year: ");
            year = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } while (isInvalidDate(day, month, year));
        return day + "/" + month + "/" + year;
    }

    private static boolean isInvalidDate(int day, int month, int year) {
        boolean isInvalid = false;
        if (day <= 0 || day > 31) {
            System.out.println("Invalid day.");
            isInvalid = true;
        }
        if (month <= 0 || month > 12) {
            System.out.println("Invalid month.");
            isInvalid = true;
        }
        if (year < 1900 || year > 2100) {
            System.out.println("Invalid year.");
            isInvalid = true;
        }
        return isInvalid;
    }
}
