package com.alodiga.app.wallet.encript;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.alodiga.app.wallet.utils.Constants;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.alodiga.app.wallet.encript.ParseLong.desencryptMD5;
import static com.alodiga.app.wallet.encript.ParseLong.ncr1pt;


/**
 * @author ltoro
 */
public class S3cur1ty3Cryt3r {

    public static String aloDesencript(String trame, String key, String reference, String k2, String vector) throws Exception {
        //TODO Incorporar Salting al key
        ParseInt encryptString = new ParseInt();
        if (!validLenght(key)) {
            throw new KeyLongException(Constants.INVALID_KEY_LONG);
        }
        String criter = "";
        String keyEn = key;
        String v = trame;
        for (int i = 0; i < keyEn.length(); i++) {
            v = ncr1pt(v, keyEn, k2);
        }
        criter = ParseInt.mkdir(v, key, k2);
        String keyy2 = key; //llave
        String iv = vector; // vector de inicialización
        criter = StringEncrypt.encrypt(keyy2, iv, criter);
        return criter;
    }

    /**
     * @param srgs
     * @param key
     * @param reference
     * @param k2
     * @param vector
     */
    public static String aloEncrpter(String srgs, String key, String reference, String k2, String vector) throws Exception {
        //TODO Incorporar Salting
        String pass = "";
        String keyy2 = key; //llave
        String iv = vector; // vector de inicialización
        srgs = StringEncrypt.decrypt(keyy2, iv, srgs);
        try {
            ParseInt encryptString = new ParseInt();
            if (!validLenght(key)) {
                throw new KeyLongException(Constants.INVALID_KEY_LONG);
            }
            String keyEn = key;
            pass = ParseInt.DesEncrypter(srgs, key, k2);
            for (int i = 0; i < keyEn.length(); i++) {
                pass = desencryptMD5(pass, keyEn, k2);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return pass;
    }

    public static boolean validLenght(String key) {
        boolean returnb = key.length() == 16L;
        return returnb;
    }

    public static void main(String[] args) {


        //String value = "maria,d,tercerparametro,p";
        String enc;
        try {
            enc = aloDesencript(Constants.VALUE_KEY, Constants.KEY_ENCRIPT_DESENCRIPT, Constants.VALUE_KEY, Constants.K2_ENCRIPT_DESENCRIPT, Constants.VECTOR_ENCRIPT_DESENCRIPT);
            System.out.println(enc);
            String desc = aloEncrpter("", Constants.KEY_ENCRIPT_DESENCRIPT, null, Constants.K2_ENCRIPT_DESENCRIPT, Constants.VECTOR_ENCRIPT_DESENCRIPT);
            System.out.println(desc);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(S3cur1ty3Cryt3r.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(S3cur1ty3Cryt3r.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(S3cur1ty3Cryt3r.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(S3cur1ty3Cryt3r.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(S3cur1ty3Cryt3r.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyLongException ex) {
            Logger.getLogger(S3cur1ty3Cryt3r.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(S3cur1ty3Cryt3r.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
