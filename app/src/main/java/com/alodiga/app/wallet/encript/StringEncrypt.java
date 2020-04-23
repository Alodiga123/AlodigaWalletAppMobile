/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alodiga.app.wallet.encript;

import com.alodiga.app.wallet.utils.Constants;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

import android.util.Base64.*;


/**
 * @author usuario
 */
public class StringEncrypt {

    public static String encrypt(String key, String iv, String cleartext) throws Exception {
        Cipher cipher = Cipher.getInstance(Constants.CI);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), Constants.ALG);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(cleartext.getBytes());
        return new String(encodeBase64(encrypted));
    }

    public static String decrypt(String key, String iv, String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(Constants.CI);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), Constants.ALG);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        Base64 prue= new Base64();
        byte[] enc = android.util.Base64.decode(encrypted,64);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(enc);
        return new String(decrypted);
    }

}
