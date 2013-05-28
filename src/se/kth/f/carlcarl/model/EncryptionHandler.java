package se.kth.f.carlcarl.model;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import javax.xml.bind.DatatypeConverter;

public class EncryptionHandler {
    public static enum Encryption {NONE, AES, CASEAR};

    public static String Encrypt(Encryption encryption, String message, String key) {
        String encryptedMessage;
        switch (encryption) {
            case AES:
                encryptedMessage = "<encrypted type=\"aes\" key=\""+ key +"\">" + EncryptAES(message, key) + "</encrypted>";
                break;
            case CASEAR:
                encryptedMessage = "<encrypted type=\"caesar\" key=\""+ key +"\">" + EncryptCaesar(message, key) + "</encrypted>";
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
        StringBuffer cipherText = new StringBuffer();
        try {
            cipher = Cipher.getInstance("AES");
            byte[] keyData = hexStringToByteArray(keyString);
            Key key = new SecretKeySpec(keyData, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherBytes = cipher.doFinal(message.getBytes("UTF-8"));

            for(byte c : cipherBytes) {
                cipherText.append(String.format("%02X", c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText.toString();
    }

    private static String EncryptCaesar(String message, String keyString) {
        byte key = (byte)Integer.parseInt(keyString);
        StringBuffer cipherText = new StringBuffer();
        for(byte b : message.getBytes()) {
            cipherText.append(String.format("%02X", b+key));
        }

        return cipherText.toString();
    }

    private static String DecryptAES(String message, String keyString) {
        Cipher cipher;
        String cipherText = "";
        try {
            cipher = Cipher.getInstance("AES");
            byte[] keyData = hexStringToByteArray(keyString);
            Key key = new SecretKeySpec(keyData, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cipherBytes = cipher.doFinal(hexStringToByteArray(message));
            cipherText = new String(cipherBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText;
    }

    private static String DecryptCaesar(String message, String keyString) {
        int key = Integer.parseInt(keyString);
        StringBuffer cipherText = new StringBuffer();

        for (int i = 0; i < message.length(); i+=2){
            char c = (char)(Integer.parseInt(message.substring(i, i+2), 16)-key);
            cipherText.append(c);
        }

        return cipherText.toString();
    }

    public static void main(String[] args) {
        String message = "<text color=\"#000000\">abcxyz</text>";
        String key1 = "ABCDABCDABCDABCDABCDABCDABCDABCD";
        String key2 = "13";

        String cipher = EncryptionHandler.Encrypt(Encryption.AES, message, key1);
        cipher = cipher.replace("<encrypted type=\"aes\">", "");
        cipher = cipher.replace("</encrypted>", "");
        String decrypt = EncryptionHandler.Decrypt(Encryption.AES, cipher, key1);

        System.out.println(message);
        System.out.println(cipher);
        System.out.println(decrypt);

        cipher = EncryptionHandler.Encrypt(Encryption.CASEAR, message, key2);
        cipher = cipher.replace("<encrypted type=\"caesar\">", "");
        cipher = cipher.replace("</encrypted>", "");
        decrypt = EncryptionHandler.Decrypt(Encryption.CASEAR, cipher, key2);

        System.out.println(message);
        System.out.println(cipher);
        System.out.println(decrypt);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
