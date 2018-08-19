package passwdencryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

    private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";

    Encryption() {
    }

    public String encrypt(String value) {

        try {

            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            Base64.Encoder b64 = Base64.getEncoder();
            return b64.encodeToString(encrypted);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String EncryptWithoutBase64(String value) {
        KeyGenerator keygenerator;
        String sql;
        try {
            keygenerator = KeyGenerator.getInstance("DES");

            SecretKey myDesKey = keygenerator.generateKey();

            Cipher desCipher;

            // Create the cipher 
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Initialize the cipher for encryption
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

            //sensitive information
            // byte[] text = "No body can see me".getBytes();
            byte[] passwdtxt = value.getBytes();

            System.out.println("Text [Byte Format] : " + passwdtxt);
            System.out.println("Text : " + new String(passwdtxt));

            // Encrypt the text
            byte[] textEncrypted = desCipher.doFinal(passwdtxt);

            // System.out.println("Text Encryted : " + (new String(textEncrypted)));
            String txtPasswd = new String(textEncrypted);
            return txtPasswd;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (BadPaddingException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

}
