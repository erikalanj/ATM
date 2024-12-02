import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.SecureRandom;

public class CryptoUtil {

    // AES Key and IV generation
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // 256-bit AES key
        return keyGen.generateKey();
    }

    public static byte[] generateIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16]; // 16-byte IV for AES
        random.nextBytes(iv);
        return iv;
    }

    // Updated CryptoUtil to handle padding correctly
    public static String encryptAES(String data, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)); // Ensure UTF-8 encoding
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decryptAES(String encryptedData, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData, StandardCharsets.UTF_8); // Ensure UTF-8 decoding
    }

    // Generate RSA key pair
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // 2048-bit RSA key
        return keyGen.generateKeyPair();
    }

    // Encrypt AES key using RSA
    public static String encryptRSA(SecretKey secretKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKey = cipher.doFinal(secretKey.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedKey);
    }

    // Decrypt AES key using RSA
    public static SecretKey decryptRSA(String encryptedKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedKey = cipher.doFinal(Base64.getDecoder().decode(encryptedKey));
        return new SecretKeySpec(decodedKey, "AES");
    }
}
