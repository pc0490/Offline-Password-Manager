import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Arrays;

public class PasswordEncryption {

    private static final String ALGORITHM = "AES";
    private static SecretKey secretKey;

    // Set a new secret key from a normal string
    public static void setSecretKey(String key) throws Exception {
        // Pad or trim the key to ensure it's 16 bytes (128 bits)
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 16) {
            // Pad with zeros if the key is too short
            keyBytes = Arrays.copyOf(keyBytes, 16);
        } else if (keyBytes.length > 16) {
            // Trim if the key is too long
            keyBytes = Arrays.copyOf(keyBytes, 16);
        }

        secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
    }

    // Encrypt the password
    public static String encrypt(String data) throws Exception {
        if (secretKey == null) {
            throw new Exception("Secret key not set. Please set a secret key before encryption.");
        }
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        // Return the encrypted data encoded in Base64
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Decrypt the password
    public static String decrypt(String encryptedData) throws Exception {
        if (secretKey == null) {
            throw new Exception("Secret key not set. Please set a secret key before decryption.");
        }
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}
