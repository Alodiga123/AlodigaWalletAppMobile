package com.alodiga.app.wallet.utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author kerwin
 */
public class AlodigaCryptographyUtils {

    public static String encrypt(String texto, String secretKey) {
        String base64EncryptedString = "";
        try {
            MessageDigest md = MessageDigest.getInstance(Constants.MD5);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, Constants.K2_ENCRIPT_DESENCRIPT);
            Cipher cipher = Cipher.getInstance(Constants.K2_ENCRIPT_DESENCRIPT);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes(StandardCharsets.UTF_8);
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return base64EncryptedString;
    }

    public static String decrypt(String textoEncriptado, String secretKey) throws Exception {
        String base64EncryptedString = "";
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes(StandardCharsets.UTF_8));
            MessageDigest md = MessageDigest.getInstance(Constants.MD5);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, Constants.K2_ENCRIPT_DESENCRIPT);

            Cipher decipher = Cipher.getInstance(Constants.K2_ENCRIPT_DESENCRIPT);
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, StandardCharsets.UTF_8);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return base64EncryptedString;
    }

}