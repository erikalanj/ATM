import java.io.*;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NewAccount {

    private static int lastAccountNumber = 10000000;
    private static Map<String, Integer> userAccountMap = new HashMap<>();
    private static String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890#_!%^&@";
    private static boolean headerWritten = false;

    public NewAccount() {
        // No need to increment `lastAccountNumber` here; handled in `generateAccountID`
    }

    public static int generateAccountID(String username) {
        if (!userAccountMap.containsKey(username)) {
            userAccountMap.put(username, lastAccountNumber++);
        }
        return userAccountMap.get(username);
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
        int accountNumber = generateAccountID(name);
        String password = generatePassword(); // Generate the plaintext password
        String hashedPassword = hashPassword(password); // Hash the password

        try (FileWriter writer = new FileWriter("accountInfo.txt", true)) {
            if (!headerWritten) {
                writer.write("Name HashedPassword AccountID Birthday\n");
                headerWritten = true;
            }
            writer.write(name + "  " + hashedPassword + "  " + accountNumber + "  " + dob + "\n");

            System.out.println("Your password is: " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getHashedPassword(String name) {
        try (Scanner scanner = new Scanner(new File("accountInfo.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\s+");
                if (parts[0].equals(name)) {
                    return parts[1]; // Return hashed password
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null; // Return null if account not found
    }

    public boolean checkAccount(String name) {
        try (FileInputStream fis = new FileInputStream("accountInfo.txt");
                Scanner scan = new Scanner(fis)) {

            while (scan.hasNextLine()) {
                if (scan.nextLine().contains(name)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
