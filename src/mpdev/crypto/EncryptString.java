package mpdev.crypto;

import javax.crypto.spec.SecretKeySpec;

public class EncryptString {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("usage: EncryptString <plain_text>");
            System.exit(1);
        }
        String plainText = args[0];
        try {
            SecretKeySpec key = Crypto.createSecretKey();
            String encryptedString = Crypto.encryptString(plainText, key);
            System.out.println("Plain Text: "+plainText);
            Crypto.printSecretKey(key);
            System.out.println("Encrypted String(Base64): "+encryptedString);
        }
        catch (Exception e) {
            System.err.println("Exception: "+e);
            System.exit(2);
        }
    }
}
