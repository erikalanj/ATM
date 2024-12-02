import java.io.*;
import java.util.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class NewAccount {

    private static int lastAccountNumber = 10000000;
    private static Map<String, Integer> userAccountMap = new HashMap<>();
    private static final String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890#_!%^&@";
    private static boolean headerWritten = false;

    // AES Key Storage
    protected static SecretKey aesKey;
    protected static byte[] iv;

    static {
        try {
            aesKey = CryptoUtil.generateAESKey();
            iv = CryptoUtil.generateIV();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize AES key and IV");
        }
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
        try {
            int accountNumber = generateAccountID(name);
            String password = generatePassword(); // Generate the plaintext password
            String hashedPassword = hashPassword(password); // Hash the password

            // Encrypt sensitive data
            String encryptedName = CryptoUtil.encryptAES(name, aesKey, iv);
            String encryptedDob = CryptoUtil.encryptAES(dob, aesKey, iv);

            try (FileWriter writer = new FileWriter("accountInfo.txt", true)) {
                if (!headerWritten) {
                    writer.write("EncryptedName EncryptedHashedPassword AccountID EncryptedDob\n");
                    headerWritten = true;
                }
                // Corrected formatting of account details
                writer.write(
                        String.format("%s  %s  %d  %s\n", encryptedName, hashedPassword, accountNumber, encryptedDob));

                System.out.println("Your password is: " + password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHashedPassword(String name) {
        try (Scanner scanner = new Scanner(new File("accountInfo.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\s+");
                if (CryptoUtil.decryptAES(parts[0], aesKey, iv).equals(name)) {
                    return parts[1]; // Return hashed password
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if account not found
    }

    public boolean checkAccount(String name) {
        try (FileInputStream fis = new FileInputStream("accountInfo.txt");
                Scanner scan = new Scanner(fis)) {

            while (scan.hasNextLine()) {
                String[] parts = scan.nextLine().split("\\s+");
                String decryptedName = CryptoUtil.decryptAES(parts[0], aesKey, iv);
                if (decryptedName.equals(name)) {
                    return true; // Account found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Account not found
    }

}
