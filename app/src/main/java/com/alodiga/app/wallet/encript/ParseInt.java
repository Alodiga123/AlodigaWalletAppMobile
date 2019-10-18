/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alodiga.app.wallet.encript;

import com.alodiga.app.wallet.utils.Constants;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author usuario
 */
public class ParseInt {

    public static String mkdir(String value, String ke, String t2) {
        String secretKey = ke; //llave para encriptar datos
        String base64EncryptedString = "";
        try {
            MessageDigest md = MessageDigest.getInstance(Constants.MD5);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, t2);
            Cipher cipher = Cipher.getInstance(t2);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainTextBytes = value.getBytes(StandardCharsets.UTF_8);
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public static String DesEncrypter(String textoEncriptado, String ke, String k2) throws Exception {
        String secretKey = ke; //llave para encriptar datos
        String base64EncryptedString = "";
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes(StandardCharsets.UTF_8));
            MessageDigest md = MessageDigest.getInstance(Constants.MD5);
            byte[] digestOfPassword = md.digest(secretKey.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, k2);
            Cipher decipher = Cipher.getInstance(k2);
            decipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = decipher.doFinal(message);
            base64EncryptedString = new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

}
