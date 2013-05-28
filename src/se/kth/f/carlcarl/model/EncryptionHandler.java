package se.kth.f.carlcarl.model;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class EncryptionHandler {
    public static enum Encryption {NONE, AES, CASEAR};

    public static String Encrypt(Encryption encryption, String message, String key) {
        String encryptedMessage;
        switch (encryption) {
            case AES:
                encryptedMessage = "<encrypted type=\"aes\">" + EncryptAES(message, key) + "</encrypted>";
                break;
            case CASEAR:
                encryptedMessage = "<encrypted type=\"caesar\">" + EncryptCaesar(message, key) + "</encrypted>";
                break;
            default:
                encryptedMessage = message;
                break;
        }

        return encryptedMessage;
    }

    public static String Decrypt(Encryption encryption, String ciphertext, String key) {
        String decryptedMessage;
        switch (encryption) {
            case AES:
                decryptedMessage = DecryptAES(ciphertext, key);
                break;
            case CASEAR:
                decryptedMessage = DecryptCaesar(ciphertext, key);
                break;
            default:
                decryptedMessage = ciphertext;
                break;
        }

        return decryptedMessage;
    }

    private static String EncryptAES(String message, String keyString) {
        Cipher cipher;
        String cipherText = "";
        try {
            cipher = Cipher.getInstance("AES");
            byte[] keyData = keyString.getBytes();
            Key key = new SecretKeySpec(keyData, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherBytes = cipher.doFinal(message.getBytes("UTF-8"));
            cipherText = new String(cipherBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText;
    }

    private static String EncryptCaesar(String message, String keyString) {
        int key = Integer.parseInt(keyString);
        StringBuffer cipherText = new StringBuffer();
        for(char c : message.toCharArray()) {
            cipherText.append(String.format("%02X", c+key));
        }

        return cipherText.toString();
    }

    private static String DecryptAES(String message, String keyString) {
        Cipher cipher;
        String painText = "";
        try {
            cipher = Cipher.getInstance("AES");
            SecretKeySpec key = new SecretKeySpec(keyString.getBytes("UTF-8"), "DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cipherBytes = cipher.doFinal(message.getBytes("UTF-8"));
            painText = new String(cipherBytes, "UTF-8");
        } catch (Exception e) {

        }

        return painText;
    }

    private static String DecryptCaesar(String message, String keyString) {
        int key = Integer.parseInt(keyString);
        StringBuffer cipherText = new StringBuffer();
        for(int i = 0; i < message.length(); i+=2) {
            char c = (char)(Integer.parseInt(message.substring(i, i+2), 16)-key);
            cipherText.append(c);
        }

        return cipherText.toString();
    }

    public static void main(String[] args) {
        String message = "<text color=\"#000000\">abc</text>";
        String key = "ABCDABCDABCDABCDABCDABCDABCDABCD";

        String cipher = EncryptionHandler.Encrypt(Encryption.AES, message, key);
        cipher = cipher.replace("<encrypted type=\"aes\">", "");
        cipher = cipher.replace("</encrypted>", "");
        String decrypt = EncryptionHandler.Decrypt(Encryption.AES, cipher, key);

        System.out.println(message);
        System.out.println(cipher);
        System.out.println(decrypt);
    }
}
