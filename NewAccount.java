import java.io.*;
import java.util.*;

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

    public static void createAccount(String name, String dob) {
        int accountNumber = generateAccountID(name);
        String password = generatePassword(); // Generate the plaintext password

        try (FileWriter writer = new FileWriter("accountInfo.txt", true)) {
            if (!headerWritten) {
                writer.write("Name Password AccountID Birthday\n");
                headerWritten = true;
            }
            writer.write(name + "  " + password + "  " + accountNumber + "  " + dob + "\n");

            System.out.println("Your password is: " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPassword(String name) {
        try (Scanner scanner = new Scanner(new File("accountInfo.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\s+");
                if (parts[0].equals(name)) {
                    return parts[1]; // Return plaintext password
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
