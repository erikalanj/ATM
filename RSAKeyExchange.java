import javax.crypto.SecretKey;
import java.security.*;

public class RSAKeyExchange {

    protected KeyPair rsaKeyPair;
    protected PublicKey publicKey;
    protected PrivateKey privateKey;

    public RSAKeyExchange() throws Exception {
        this.rsaKeyPair = CryptoUtil.generateRSAKeyPair();
        this.publicKey = rsaKeyPair.getPublic();
        this.privateKey = rsaKeyPair.getPrivate();
    }

    public String encryptAESKey(SecretKey aesKey) throws Exception {
        return CryptoUtil.encryptRSA(aesKey, publicKey);
    }

    public SecretKey decryptAESKey(String encryptedKey) throws Exception {
        return CryptoUtil.decryptRSA(encryptedKey, privateKey);
    }
}
