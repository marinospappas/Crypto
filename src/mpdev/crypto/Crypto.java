package mpdev.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.util.Base64;

/**
 * class Crypto
 * encrypts and decrypts strings using AES 256 algorithm
 * @author Marinos Pappas
 * @version 2.0 01.10.2021
 */
public class Crypto {

    // secret key parameters
    private static final String secretKeyPassword = "Th!sIs4heB65tP@ffwo%";
    private static final byte[] salt = {81,-2,43,65,2,3,-126,81,23,12,120,127,6,1,94,-9};
    private static final int iterationCount = 129582;
    private static final int keyLength = 256;

    /** generate encryption key */
    static SecretKeySpec createSecretKey() throws Exception {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(secretKeyPassword.toCharArray(), salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    /** encrypt string */
    static String encryptString(String plainText, SecretKeySpec key) throws Exception {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] iv = ivParameterSpec.getIV();
        return Base64.getEncoder().encodeToString(iv)+":"+Base64.getEncoder().encodeToString(cryptoText);
    }

    /** decrypt string */
    static String decryptString(String encryptedText, SecretKeySpec key) throws Exception {
        String iv = encryptedText.split(":")[0];
        String property = encryptedText.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Base64.getDecoder().decode(iv)));
        return new String(pbeCipher.doFinal(Base64.getDecoder().decode(property)), StandardCharsets.UTF_8);
    }

    /** print secret key */
    static void printSecretKey(SecretKeySpec key) {
        System.out.print("Secret key(Hex): ");
        for (byte aByte : key.getEncoded())
            System.out.printf("%02X", (int)aByte & 0xFF);
        System.out.println();
    }
}
