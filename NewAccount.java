import java.io.*;
import java.util.*;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NewAccount {

    private static int lastAccountNumber = 10000000;

    private int accountNumber;

    private static String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890#_!%^&@";

    private String name;

    private static boolean headerWritten = false;

    public NewAccount() {

        this.accountNumber = lastAccountNumber++;

    }

    public static String generatePassword() {

        StringBuilder password = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 8; i++) {

            password.append(alphabets.charAt(random.nextInt(alphabets.length())));

        }

        return password.toString();

    }

    public static String hashPassword(String password) {

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hashedPassword = new StringBuilder();
            for (byte b : hashedBytes) {
                hashedPassword.append(String.format("%02x", b));
            }
            return hashedPassword.toString();

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            return null;

        }

    }

    public static void createAccount(String name, String dob) {

        NewAccount newAccount = new NewAccount();

        int accountNumber = newAccount.accountNumber;

        String password = generatePassword();
        String hashedPassword = hashPassword(password);

        AccessAccount accessAccount = new AccessAccount(name, accountNumber, dob);

        try (FileWriter writer = new FileWriter("accountInfo.txt", true)) {

            if (!headerWritten) {

                writer.write("Username HashedPassword AccountID DateOfBirth\n");

                headerWritten = true;

            }

            writer.write(name + "  " + hashedPassword + "  " + accountNumber + "  " + dob + "\n");

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static int getLastAccountNumber() {

        return lastAccountNumber;

    }

    boolean checkAccount(String name) {

        boolean flag = false;

        try {

            FileInputStream fis = new FileInputStream("accountinfo.txt");

            Scanner scan = new Scanner(fis);

            ArrayList<String> list = new ArrayList<>();

            while (scan.hasNextLine()) {

                list.add(scan.nextLine());

            }

            scan.close();

            for (String x : list) {

                if (x.contains(name)) {
                    flag = true;
                }

            }

        } catch (Exception e) {

        }

        return flag;

    }
}