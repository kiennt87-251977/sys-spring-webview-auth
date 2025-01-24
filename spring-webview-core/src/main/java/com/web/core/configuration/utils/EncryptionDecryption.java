package com.web.core.configuration.utils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionDecryption {

    private static Cipher cipher;
    private static final String algorithm = "AES";
    public static final String keySplit = "ACBACBACB";
    public static final String keySplit02 = "\\$AAAAA\\$";

    static {
        try {
            cipher = Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    private static SecretKey secretKey() {
        int a = 16;
        byte[] var = new byte[a];
        for (int i = 0; i < a; i++) {
            var[i] = (byte) (2 * i + 1);
        }
        return new SecretKeySpec(var, algorithm);
    }

    public static String encrypt(String plainText)
            throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey());
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public static String decrypt(String encryptedText)
            throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, secretKey());
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }


}