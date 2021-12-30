package mpdev.crypto;

import javax.crypto.spec.SecretKeySpec;

public class DecryptString {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("usage: DecryptString <encrypted_string>");
            System.exit(1);
        }
        String encryptedText = args[0];
        try {
            SecretKeySpec key = Crypto.createSecretKey();
            String plainText = Crypto.decryptString(encryptedText, key);
            System.out.println("Encrypted String(Base64): "+encryptedText);
            Crypto.printSecretKey(key);
            System.out.println("Plain Text: "+plainText);
        }
        catch (Exception e) {
            System.err.println("Exception: "+e);
            System.exit(2);
        }
    }
}